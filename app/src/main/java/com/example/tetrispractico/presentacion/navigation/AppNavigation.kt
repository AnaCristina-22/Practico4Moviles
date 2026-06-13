package com.example.tetrispractico.presentacion.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.tetrispractico.presentacion.screens.create.CreateRoomScreen
import com.example.tetrispractico.presentacion.screens.home.HomeScreen
import com.example.tetrispractico.presentacion.screens.join.JoinRoomScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {

            HomeScreen(

                onCreateRoom = {
                    navController.navigate(
                        Screen.CreateRoom.route
                    )
                },

                onJoinRoom = {
                    navController.navigate(
                        Screen.JoinRoom.route
                    )
                }
            )
        }

        composable(
            Screen.CreateRoom.route
        ) {
            CreateRoomScreen()
        }

        composable(
            Screen.JoinRoom.route
        ) {
            JoinRoomScreen()
        }
    }
}