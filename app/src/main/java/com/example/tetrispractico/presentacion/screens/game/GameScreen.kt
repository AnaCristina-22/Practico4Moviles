package com.example.tetrispractico.presentacion.screens.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tetrispractico.presentacion.components.BoardCanvas
import com.example.tetrispractico.presentacion.components.ControlButtons
import com.example.tetrispractico.presentacion.components.NextPiecePreview
import com.example.tetrispractico.presentacion.components.StatsPanel
import com.example.tetrispractico.presentacion.models.GameBoardState

@Composable
fun GameScreen(
    score: Int = 0,
    lines: Int = 0,
    countdown: Int = 0,
    connected: Boolean = true,
    opponentConnected: Boolean = true,
    boardState: GameBoardState = GameBoardState(),
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {},
    onDownClick: () -> Unit = {},
    onRotateLeftClick: () -> Unit = {},
    onRotateRightClick: () -> Unit = {},
    onLeaveGame: () -> Unit = {}
) {
    BackHandler {
        onLeaveGame()
    }

    // Usamos un Box principal para poder encimar la capa de la cuenta regresiva
    Box(modifier = Modifier.fillMaxSize()) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Card(modifier = Modifier.weight(1f)) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("TETRIS DUEL")
                        Text(if (connected) "🟢 Conectado" else "🔴 Desconectado")
                        Text(if (opponentConnected) "👤 Rival listo" else "👤 Rival fuera")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("⭐ Objetivo: 37")
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))
                StatsPanel(score = score, lines = lines)
                Spacer(modifier = Modifier.width(8.dp))
                NextPiecePreview(boardState = boardState)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                BoardCanvas(boardState = boardState)
            }

            Spacer(modifier = Modifier.height(16.dp))

            ControlButtons(
                onLeftClick = onLeftClick,
                onRightClick = onRightClick,
                onDownClick = onDownClick,
                onRotateLeftClick = onRotateLeftClick,
                onRotateRightClick = onRotateRightClick
            )
        }

       //Cuenta regresiva
        if (countdown > 0) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "¡Empieza el juego en...",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "$countdown",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}