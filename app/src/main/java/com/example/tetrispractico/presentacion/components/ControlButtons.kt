package com.example.tetrispractico.presentacion.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ControlButtons(
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {},
    onDownClick: () -> Unit = {},
    onRotateLeftClick: () -> Unit = {},
    onRotateRightClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        // Bloque Izquierdo: Movimiento
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onDownClick,
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(8.dp)

            ) {
                Text("↓")
            }

            Row {
                Button(
                    onClick = onLeftClick,
                    modifier = Modifier.size(56.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("←")
                }

                Spacer(modifier = Modifier.width(width = 8.dp))

                Button(
                    onClick = onRightClick,
                    modifier = Modifier.size(56.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("→")
                }
            }
        }

        // Bloque Derecho: Rotación
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onRotateLeftClick,
                    modifier = Modifier.height(56.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("↺")
                }
                Text("Gira Izq", style = MaterialTheme.typography.labelSmall)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onRotateRightClick,
                    modifier = Modifier.height(56.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("↻")
                }
                Text("Gira Der", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}