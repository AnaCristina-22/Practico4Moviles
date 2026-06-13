package com.example.tetrispractico.presentacion.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ControlButtons() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Button(onClick = {}) {
            Text("←")
        }

        Button(onClick = {}) {
            Text("↻")
        }

        Button(onClick = {}) {
            Text("→")
        }

        Button(onClick = {}) {
            Text("↓")
        }
    }
}