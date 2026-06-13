package com.example.tetrispractico.presentacion.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BoardCanvas() {

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {

        val cols = 10
        val rows = 20

        val cellWidth = size.width / cols
        val cellHeight = size.height / rows

        for (row in 0 until rows) {

            for (col in 0 until cols) {

                var color = Color(0xFF2E2E2E)

                if (
                    (row == 18 && col in 2..5) ||
                    (row == 17 && col == 4)
                ) {
                    color = Color.Cyan
                }

                drawRect(
                    color = color,
                    topLeft = Offset(
                        col * cellWidth,
                        row * cellHeight
                    ),
                    size = Size(
                        cellWidth - 2,
                        cellHeight - 2
                    )
                )
            }
        }
    }
}