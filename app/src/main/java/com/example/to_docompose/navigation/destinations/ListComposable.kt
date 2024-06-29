package com.example.to_docompose.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_docompose.ui.screens.list.ListScreen
import com.example.to_docompose.util.Action
import com.example.to_docompose.util.Constants.LIST_ARGUMENT_KEY
import com.example.to_docompose.util.Constants.LIST_SCREEN


fun NavGraphBuilder.listComposable() {
    composable(
        LIST_SCREEN,
        arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) {
        ListScreen()
    }
}

fun NavHostController.navigateToListScreen(action: Action) {
    this.navigate("list/${action.name}") {
        popUpTo(LIST_SCREEN) { inclusive = true }
    }
}