package com.example.tetrispractico.presentacion.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BoardCanvas() {

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {

        val cellWidth = size.width / 10f
        val cellHeight = size.height / 20f

        for (row in 0 until 20) {

            for (col in 0 until 10) {

                drawRect(
                    color = Color.DarkGray,
                    topLeft = androidx.compose.ui.geometry.Offset(
                        col * cellWidth,
                        row * cellHeight
                    ),
                    size = androidx.compose.ui.geometry.Size(
                        cellWidth - 2,
                        cellHeight - 2
                    )
                )
            }
        }
    }
}