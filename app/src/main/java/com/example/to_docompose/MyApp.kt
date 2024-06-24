package com.example.to_docompose

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.to_docompose.navigation.SetupNavigation
import com.example.to_docompose.ui.theme.ToDoComposeTheme


@Composable
fun MyApp() {
    val navController = rememberNavController()

    ToDoComposeTheme {
        SetupNavigation(navController)
    }
}