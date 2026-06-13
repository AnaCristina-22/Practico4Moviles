package com.example.tetrispractico.presentacion.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NextPiecePreview() {

    Column {

        Text("SIGUIENTE")

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Canvas(
            modifier = Modifier.size(80.dp)
        ) {

            val block = size.width / 4

            drawRect(
                Color.Yellow,
                Offset(block, block),
                Size(block, block)
            )

            drawRect(
                Color.Yellow,
                Offset(block * 2, block),
                Size(block, block)
            )

            drawRect(
                Color.Yellow,
                Offset(block, block * 2),
                Size(block, block)
            )

            drawRect(
                Color.Yellow,
                Offset(block * 2, block * 2),
                Size(block, block)
            )
        }
    }
}