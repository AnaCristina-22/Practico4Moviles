package com.example.tetrispractico.presentacion.navigation

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.tetrispractico.ViewModel.TetrisState
import com.example.tetrispractico.data.remote.SocketHandler
import com.example.tetrispractico.data.repository.TetrisRepository
import com.example.tetrispractico.presentacion.screens.create.CreateRoomScreen
import com.example.tetrispractico.presentacion.screens.game.GameScreen
import com.example.tetrispractico.presentacion.screens.home.HomeScreen
import com.example.tetrispractico.presentacion.screens.join.JoinRoomScreen
import com.example.tetrispractico.presentacion.screens.result.ResultScreen
import com.example.tetrispractico.presentacion.viewmodel.TetrisViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()


    // Instanciamos la red y el repositorio
    val socketHandler = remember { SocketHandler() }
    val repository = remember { TetrisRepository(socketHandler) }

    // Creamos el ViewModel pasándole el repositorio mediante un Factory
    val viewModel: TetrisViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TetrisViewModel(repository) as T
            }
        }
    )

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        when (uiState) {
            is TetrisState.Playing -> {
                navController.navigate(Screen.Game.route) {
                    popUpTo(Screen.Home.route) { inclusive = false }
                }
            }
            is TetrisState.Won, is TetrisState.Lost -> {
                navController.navigate(Screen.Result.route) {
                    popUpTo(Screen.Game.route) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onCreateRoom = {
                    viewModel.createRoom()
                    navController.navigate(Screen.CreateRoom.route)
                },
                onJoinRoom = {
                    navController.navigate(Screen.JoinRoom.route)
                }
            )
        }

        composable(Screen.CreateRoom.route) {
            val roomId by viewModel.currentRoomId.collectAsState()
            CreateRoomScreen(roomId = roomId)
        }

        composable(Screen.JoinRoom.route) {
            JoinRoomScreen(viewModel = viewModel)
        }

        composable(Screen.Game.route) {
            val score by viewModel.score.collectAsState()
            val lines by viewModel.lines.collectAsState()
            val boardState by viewModel.boardState.collectAsState()
            val countdown by viewModel.countdown.collectAsState()

            GameScreen(
                score = score,
                lines = lines,
                countdown = countdown,
                boardState = boardState,
                onLeftClick = { viewModel.moveLeft() },
                onRightClick = { viewModel.moveRight() },
                onDownClick = { viewModel.moveDown() },
                onRotateLeftClick = { viewModel.rotateLeft() },
                onRotateRightClick = { viewModel.rotateRight() },
                onLeaveGame = {
                    viewModel.surrender()
                    navController.navigate(Screen.Home.route) { popUpTo(0) }
                }
            )
        }

        composable(Screen.Result.route) {
            val score by viewModel.score.collectAsState()
            val lines by viewModel.lines.collectAsState()

            val isWinner = uiState is TetrisState.Won
            val message = when (val state = uiState) {
                is TetrisState.Won -> state.message
                is TetrisState.Lost -> state.message
                else -> ""
            }

            ResultScreen(
                isWinner = isWinner,
                score = score,
                lines = lines,
                duration = viewModel.getGameDuration(),
                message = message,
                onBackToHome = {
                    viewModel.resetGame()
                    navController.navigate(Screen.Home.route) { popUpTo(0) }
                }
            )
        }
    }
}