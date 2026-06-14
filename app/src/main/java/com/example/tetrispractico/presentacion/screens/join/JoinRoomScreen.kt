package com.example.tetrispractico.presentacion.screens.join

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tetrispractico.presentacion.viewmodel.TetrisViewModel

@Composable
fun JoinRoomScreen(viewModel: TetrisViewModel) {
    var roomCode by remember { mutableStateOf("") }
    var localError by remember { mutableStateOf("") }
    val serverError by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = roomCode,
            onValueChange = {
                roomCode = it.uppercase()
                localError = ""
            },
            label = { Text("Código de Sala") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Mensajes de error
        if (localError.isNotEmpty()) {
            Text(localError, color = Color.Red)
        } else if (serverError.isNotEmpty()) {
            Text(serverError, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (roomCode.isBlank()) {
                    localError = "El código no puede estar vacío"
                } else {
                    viewModel.joinRoom(roomCode)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Conectarse")
        }
    }
}