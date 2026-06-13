package com.example.tetrispractico.presentacion.screens.join


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun JoinRoomScreen() {

    var roomCode by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = roomCode,
            onValueChange = {
                roomCode = it
            },

            label = {
                Text("Código de Sala")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Conectarse")
        }
    }
}