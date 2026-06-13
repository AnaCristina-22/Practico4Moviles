package com.example.tetrispractico.presentacion.screens.game

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tetrispractico.presentacion.components.BoardCanvas
import com.example.tetrispractico.presentacion.components.NextPiecePreview
import com.example.tetrispractico.presentacion.components.StatsPanel

@Composable
fun GameScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

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

                StatsPanel()

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                NextPiecePreview()
            }
        }

        Text("⭐ Objetivo 37")

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text("←   ↻   →   ↓")
    }
}