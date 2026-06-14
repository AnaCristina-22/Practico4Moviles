package com.example.tetrispractico.presentacion.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import com.example.tetrispractico.presentacion.models.GameBoardState

@Composable
fun NextPiecePreview(boardState: GameBoardState) {
    Column {
        Text("SIGUIENTE")
        Spacer(modifier = Modifier.height(8.dp))

        Canvas(modifier = Modifier.size(80.dp)) {
            val blockSize = size.width / 4

            // Dibujamos bloque a bloque según la matriz de la siguiente pieza
            boardState.nextPiecePreview.forEach { block ->
                drawRect(
                    color = block.color,
                    topLeft = Offset(block.x * blockSize, block.y * blockSize),
                    size = Size(blockSize - 2, blockSize - 2)
                )
            }
        }
    }
}