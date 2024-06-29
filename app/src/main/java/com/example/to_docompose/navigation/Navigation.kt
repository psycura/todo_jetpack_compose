package com.example.to_docompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.to_docompose.navigation.destinations.listComposable
import com.example.to_docompose.navigation.destinations.taskComposable
import com.example.to_docompose.util.Constants.LIST_SCREEN

@Composable
fun SetupNavigation(navController: NavHostController) {

    NavHost(
        navController, startDestination = LIST_SCREEN
    ) {
        listComposable()
        taskComposable()
    }
}
