package com.example.tetrispractico.presentacion.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ConnectionIndicator(
    connected: Boolean = true
) {

    if (connected) {
        Text("🟢 Conectado")
    } else {
        Text("🔴 Desconectado")
    }
}