package com.example.tetrispractico.presentacion.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatsPanel(
    score: Int,
    lines: Int
) {

    Card {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text("PUNTAJE")
            Text("$score")

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text("LÍNEAS")
            Text("$lines")
        }
    }
}