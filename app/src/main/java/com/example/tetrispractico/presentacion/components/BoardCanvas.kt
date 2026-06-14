package com.example.tetrispractico.presentacion.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.tetrispractico.presentacion.models.GameBoardState

@Composable
fun BoardCanvas(boardState: GameBoardState) {

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {
        val cols = 10
        val rows = 20
        val cellWidth = size.width / cols
        val cellHeight = size.height / rows

        // 1. Dibujar el fondo del tablero
        for (row in 0 until rows) {
            for (col in 0 until cols) {
                drawRect(
                    color = Color.DarkGray,
                    topLeft = Offset(col * cellWidth, row * cellHeight),
                    size = Size(cellWidth, cellHeight),
                    style = Stroke(width = 1f)
                )

                // Si la matriz indica que hay un color guardado aquí, lo pintamos
                val cellColor = boardState.grid[row][col]
                if (cellColor != Color.Transparent) {
                    drawRect(
                        color = cellColor,
                        topLeft = Offset(col * cellWidth, row * cellHeight),
                        size = Size(cellWidth - 2, cellHeight - 2)
                    )
                }
            }
        }

        // 2. Dibujar la pieza que está cayendo en este momento
        boardState.fallingPiece.forEach { block ->
            drawRect(
                color = block.color,
                topLeft = Offset(block.x * cellWidth, block.y * cellHeight),
                size = Size(cellWidth - 2, cellHeight - 2)
            )
        }
    }
}