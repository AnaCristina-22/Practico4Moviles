package com.example.tetrispractico.presentacion.screens.game

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tetrispractico.presentacion.components.BoardCanvas
import com.example.tetrispractico.presentacion.components.ControlButtons
import com.example.tetrispractico.presentacion.components.NextPiecePreview
import com.example.tetrispractico.presentacion.components.StatsPanel

@Composable
fun GameScreen(
    score: Int = 0,
    lines: Int = 0,
    connected: Boolean = true,
    opponentConnected: Boolean = true
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(12.dp)
            ) {

                Text("TETRIS DUEL")

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    if (connected)
                        "🟢 Conectado"
                    else
                        "🔴 Desconectado"
                )

                Text(
                    if (opponentConnected)
                        "👤 Rival conectado"
                    else
                        "👤 Rival desconectado"
                )
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Row(
            modifier = Modifier.weight(1f)
        ) {

            Box(
                modifier = Modifier.weight(3f)
            ) {

                BoardCanvas()
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                StatsPanel(
                    score = score,
                    lines = lines
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                NextPiecePreview()
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text("⭐ Objetivo especial: 37")

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        ControlButtons()
    }
}