package com.example.tetrispractico.presentacion.components


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NextPiecePreview() {

    Column {

        Text("Siguiente")

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Box(
            modifier = Modifier
                .size(80.dp)
                .border(
                    width = 1.dp,
                    color = androidx.compose.ui.graphics.Color.White
                )
        )
    }
}