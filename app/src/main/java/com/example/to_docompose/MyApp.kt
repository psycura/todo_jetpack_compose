package com.example.to_docompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.to_docompose.navigation.SetupNavigation
import com.example.to_docompose.ui.theme.ToDoComposeTheme

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("NavController not provided")
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    ToDoComposeTheme {
        CompositionLocalProvider(LocalNavController provides navController) {
            SetupNavigation(navController)
        }
    }
}