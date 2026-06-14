package com.example.tetrispractico.presentacion.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tetrispractico.ViewModel.TetrisState
import com.example.tetrispractico.data.repository.TetrisRepository
import com.example.tetrispractico.presentacion.models.ActivePiece
import com.example.tetrispractico.presentacion.models.GameBoardState
import com.example.tetrispractico.presentacion.models.TetrominoType
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject


class TetrisViewModel(private val repository: TetrisRepository) : ViewModel() {

    // Estados de la interfaz
    private val _uiState = MutableStateFlow<TetrisState>(TetrisState.Idle)
    val uiState: StateFlow<TetrisState> = _uiState

    private val _currentRoomId = MutableStateFlow("")
    val currentRoomId: StateFlow<String> = _currentRoomId

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    // Estadísticas
    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _lines = MutableStateFlow(0)
    val lines: StateFlow<Int> = _lines

    // Estado del tablero
    private val _boardState = MutableStateFlow(GameBoardState())
    val boardState: StateFlow<GameBoardState> = _boardState

    // Cuenta regresiva
    private val _countdown = MutableStateFlow(0)
    val countdown: StateFlow<Int> = _countdown

    // Variables internas para la lógica
    private var currentPiece: ActivePiece? = null
    private var nextPieceType: TetrominoType = TetrominoType.values().random()
    private var gameLoopJob: Job? = null
    private var startTime: Long = 0L

    init {
        // Usamos el repositorio en lugar del socketHandler directamente
        repository.connect("http://192.168.1.221:3000")

        repository.getSocket()?.on("room_created") { args ->
            if (args.isNotEmpty()) {
                val data = args[0] as JSONObject
                _currentRoomId.value = data.getString("roomId")
            }
        }

        repository.getSocket()?.on("game_start") {
            _uiState.value = TetrisState.Playing
            startCountdown()
        }

        repository.getSocket()?.on("receive_attack") { args ->
            if (args.isNotEmpty()) {
                val data = args[0] as JSONObject
                val garbageLines = data.getInt("garbageLines")
                viewModelScope.launch {
                    receiveGarbage(garbageLines)
                }
            }
        }

        repository.getSocket()?.on("victory") {
            _uiState.value = TetrisState.Won(_score.value, _lines.value)
        }

        repository.getSocket()?.on("opponent_disconnected") {
            _uiState.value = TetrisState.Won(_score.value, _lines.value, "El rival abandonó la partida")
            gameLoopJob?.cancel()
        }

        repository.getSocket()?.on("error_message") { args ->
            if (args.isNotEmpty()) {
                val data = args[0] as JSONObject
                _errorMessage.value = data.getString("message")
                _uiState.value = TetrisState.Idle
            }
        }
    }

    // --- CUENTA REGRESIVA ---
    private fun startCountdown() {
        viewModelScope.launch {
            for (i in 3 downTo 1) {
                _countdown.value = i
                delay(1000)
            }
            _countdown.value = 0

            startTime = System.currentTimeMillis()
            spawnPiece()
            startGameLoop()
        }
    }

    // --- REINICIAR PARTIDA ---
    fun resetGame() {
        _score.value = 0
        _lines.value = 0
        _countdown.value = 0
        _boardState.value = GameBoardState()
        _errorMessage.value = ""
        _uiState.value = TetrisState.Idle
        currentPiece = null
        gameLoopJob?.cancel()
        startTime = 0L
        nextPieceType = TetrominoType.values().random()
    }

    fun surrender() {
        repository.disconnect()
        resetGame()
        repository.connect("http://192.168.1.221:3000")
    }

    // --- CRONÓMETRO ---
    fun getGameDuration(): String {
        if (startTime == 0L) return "00:00"
        val elapsed = System.currentTimeMillis() - startTime
        val seconds = (elapsed / 1000) % 60
        val minutes = (elapsed / (1000 * 60)) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    // --- MOTOR DEL JUEGO: GRAVEDAD Y COLISIONES ---

    private fun startGameLoop() {
        gameLoopJob?.cancel()
        gameLoopJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                movePieceDown()
            }
        }
    }

    private fun movePieceDown() {
        val piece = currentPiece ?: return

        if (canMove(piece.x, piece.y + 1, piece.shape)) {
            piece.y += 1
            updateBoardUI()
        } else {
            lockPiece()
        }
    }

    private fun canMove(newX: Int, newY: Int, shape: Array<IntArray>): Boolean {
        val grid = _boardState.value.grid
        for (row in shape.indices) {
            for (col in shape[row].indices) {
                if (shape[row][col] == 1) {
                    val boardX = newX + col
                    val boardY = newY + row

                    if (boardX < 0 || boardX >= 10 || boardY >= 20) return false
                    if (boardY >= 0 && grid[boardY][boardX] != Color.Transparent) return false
                }
            }
        }
        return true
    }

    private fun lockPiece() {
        val piece = currentPiece ?: return

        val newGrid = _boardState.value.grid.map { it.toMutableList() }.toMutableList()

        for (row in piece.shape.indices) {
            for (col in piece.shape[row].indices) {
                if (piece.shape[row][col] == 1) {
                    val boardY = piece.y + row
                    val boardX = piece.x + col
                    if (boardY in 0..19 && boardX in 0..9) {
                        newGrid[boardY][boardX] = piece.type.color
                    }
                }
            }
        }

        var linesCleared = 0
        val finalGrid = mutableListOf<List<Color>>()

        for (row in newGrid) {
            if (row.contains(Color.Transparent)) {
                finalGrid.add(row)
            } else {
                linesCleared++
            }
        }

        for (i in 0 until linesCleared) {
            finalGrid.add(0, List(10) { Color.Transparent })
        }

        if (linesCleared > 0) {
            val newTotalLines = _lines.value + linesCleared
            val newScore = _score.value + (linesCleared * 100)
            updateStats(newScore, newTotalLines)

            val garbageToSend = when (linesCleared) {
                1 -> 0
                2 -> 1
                3 -> 2
                4 -> 4
                else -> 0
            }

            if (garbageToSend > 0 && _currentRoomId.value.isNotEmpty()) {
                sendAttack(_currentRoomId.value, garbageToSend)
            }
        }

        _boardState.value = _boardState.value.copy(grid = finalGrid)
        spawnPiece()
    }

    private fun receiveGarbage(garbageLines: Int) {
        val currentGrid = _boardState.value.grid.map { it.toMutableList() }.toMutableList()

        for (i in 0 until (20 - garbageLines)) {
            currentGrid[i] = currentGrid[i + garbageLines]
        }

        for (i in (20 - garbageLines) until 20) {
            val newRow = MutableList(10) { Color.Gray }
            val emptyCol = (0..9).random()
            newRow[emptyCol] = Color.Transparent
            currentGrid[i] = newRow
        }

        _boardState.value = _boardState.value.copy(grid = currentGrid)
    }

    // --- FÁBRICA DE PIEZAS ---

    private fun spawnPiece() {
        currentPiece = ActivePiece(nextPieceType, 3, 0)

        if (!canMove(currentPiece!!.x, currentPiece!!.y, currentPiece!!.shape)) {
            gameLoopJob?.cancel()

            val data = JSONObject().put("roomId", _currentRoomId.value)
            repository.getSocket()?.emit("game_over", data)

            _uiState.value = TetrisState.Lost("¡Te has quedado sin espacio!")
            return
        }

        var newRandom = TetrominoType.values().random()
        while (newRandom == nextPieceType) {
            newRandom = TetrominoType.values().random()
        }
        nextPieceType = newRandom

        updateBoardUI()
    }

    private fun updateBoardUI() {
        val nextBlocks = ActivePiece(nextPieceType, 0, 0).getBlocks()

        _boardState.value = _boardState.value.copy(
            fallingPiece = currentPiece?.getBlocks() ?: emptyList(),
            nextPiecePreview = nextBlocks
        )
    }

    // --- CONTROLES DEL JUGADOR ---

    fun moveLeft() {
        val piece = currentPiece ?: return
        if (canMove(piece.x - 1, piece.y, piece.shape)) {
            piece.x -= 1
            updateBoardUI()
        }
    }

    fun moveRight() {
        val piece = currentPiece ?: return
        if (canMove(piece.x + 1, piece.y, piece.shape)) {
            piece.x += 1
            updateBoardUI()
        }
    }

    fun moveDown() {
        movePieceDown()
    }

    fun rotateRight() {
        val piece = currentPiece ?: return
        if (piece.type == TetrominoType.O) return

        val newShape = rotateMatrix(piece.shape, clockwise = true)
        if (canMove(piece.x, piece.y, newShape)) {
            piece.shape = newShape
            updateBoardUI()
        }
    }

    fun rotateLeft() {
        val piece = currentPiece ?: return
        if (piece.type == TetrominoType.O) return

        val newShape = rotateMatrix(piece.shape, clockwise = false)
        if (canMove(piece.x, piece.y, newShape)) {
            piece.shape = newShape
            updateBoardUI()
        }
    }

    private fun rotateMatrix(matrix: Array<IntArray>, clockwise: Boolean): Array<IntArray> {
        val n = matrix.size
        val result = Array(n) { IntArray(n) }
        for (i in 0 until n) {
            for (j in 0 until n) {
                if (clockwise) {
                    result[j][n - 1 - i] = matrix[i][j]
                } else {
                    result[n - 1 - j][i] = matrix[i][j]
                }
            }
        }
        return result
    }

    // --- MÉTODOS DE RED ---

    fun createRoom() {
        _errorMessage.value = ""
        repository.getSocket()?.emit("create_room")
        _uiState.value = TetrisState.Waiting
    }

    fun joinRoom(roomId: String) {
        _errorMessage.value = ""
        _currentRoomId.value = roomId
        val data = JSONObject()
        data.put("roomId", roomId)
        repository.getSocket()?.emit("join_room", data)
        _uiState.value = TetrisState.Waiting
    }

    fun sendAttack(roomId: String, garbageLines: Int) {
        val data = JSONObject()
        data.put("roomId", roomId)
        data.put("garbageLines", garbageLines)
        repository.getSocket()?.emit("send_attack", data)
    }

    fun updateStats(newScore: Int, newLines: Int) {
        _score.value = newScore
        _lines.value = newLines
    }

    override fun onCleared() {
        super.onCleared()
        repository.disconnect()
        gameLoopJob?.cancel()
    }
}