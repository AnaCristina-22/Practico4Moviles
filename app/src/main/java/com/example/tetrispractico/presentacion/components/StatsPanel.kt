package com.example.tetrispractico.presentacion.components


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatsPanel() {

    Column {

        Text("Puntaje")
        Text("0")

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text("Líneas")
        Text("0")

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text("🟢 Conectado")

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text("👤 Rival")
    }
}