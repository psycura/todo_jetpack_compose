package com.example.to_docompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.to_docompose.navigation.destinations.listComposable
import com.example.to_docompose.navigation.destinations.taskComposable
import com.example.to_docompose.ui.viewmodels.SharedViewModel
import com.example.to_docompose.util.Constants.LIST_SCREEN
import org.koin.androidx.compose.koinViewModel

@Composable
fun SetupNavigation(navController: NavHostController) {
    val screens = remember(navController) {
        Screens(navController)
    }

    val sharedViewModel: SharedViewModel = koinViewModel()


    NavHost(
        navController, startDestination = LIST_SCREEN
    ) {
        listComposable(
            navigateToTaskScreen = screens.task,
            sharedViewModel
        )

        taskComposable(
            navigateToListScreen = screens.list,
            sharedViewModel,
        )
    }
}