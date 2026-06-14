package com.example.tetrispractico.presentacion.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tetrispractico.data.remote.SocketManager

@Composable
fun HomeScreen(
    onCreateRoom: () -> Unit = {},
    onJoinRoom: () -> Unit = {}
) {

    LaunchedEffect(Unit) {
        SocketManager.connect()
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("TETRIS DUEL", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(40.dp))

        Button(onClick = onCreateRoom, modifier = Modifier.fillMaxWidth()) {
            Text("Crear Sala")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onJoinRoom, modifier = Modifier.fillMaxWidth()) {
            Text("Unirse a Sala")
        }
    }
}
