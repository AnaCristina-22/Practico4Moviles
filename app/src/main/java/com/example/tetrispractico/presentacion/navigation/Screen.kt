package com.example.tetrispractico.presentacion.navigation

sealed class Screen(val route: String) {

    object Home : Screen("home")

    object CreateRoom : Screen("create_room")

    object JoinRoom : Screen("join_room")

    object Game : Screen("game")

    object Result : Screen("result")
}