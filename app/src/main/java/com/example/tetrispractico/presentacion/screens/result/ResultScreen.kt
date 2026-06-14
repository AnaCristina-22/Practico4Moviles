package com.example.tetrispractico.presentacion.screens.result

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(
    isWinner: Boolean,
    score: Int,
    lines: Int,
    duration: String,
    message: String = "",
    onBackToHome: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = if (isWinner) "¡GANASTE!" else "FIN DEL JUEGO",
            style = MaterialTheme.typography.headlineLarge,
            color = if (isWinner) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )


        if (message.isNotEmpty() && !isWinner) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = message, style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Estadísticas
        Text("Puntaje Obtenido: $score", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Líneas Eliminadas: $lines", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Duración: $duration", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(50.dp))


        Button(
            onClick = onBackToHome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al Menú Principal")
        }
    }
}