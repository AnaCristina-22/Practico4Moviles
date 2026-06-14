package com.example.tetrispractico.presentacion.screens.create

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateRoomScreen(roomId: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(text = "Código de Sala")
        Spacer(modifier = Modifier.height(20.dp))


        Text(
            text = roomId.ifEmpty { "Generando..." },
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Esperando jugador...")

        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Dato curioso: Solo el 37% de los jugadores ganan su primera partida.")
    }
}