package com.example.to_docompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.to_docompose.ui.screens.list.ListScreen
import com.example.to_docompose.ui.screens.task.TaskScreen
import com.example.to_docompose.ui.theme.ToDoComposeTheme
import com.example.to_docompose.ui.viewmodels.SharedViewModel
import com.example.to_docompose.util.Action
import com.example.to_docompose.util.Constants.LIST_ARGUMENT_KEY
import com.example.to_docompose.util.Constants.LIST_SCREEN
import com.example.to_docompose.util.Constants.TASK_ARGUMENT_KEY
import com.example.to_docompose.util.Constants.TASK_SCREEN
import com.example.to_docompose.util.toAction
import org.koin.androidx.compose.koinViewModel

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("NavController not provided")
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = koinViewModel()
    ToDoComposeTheme {
        CompositionLocalProvider(
            LocalNavController provides navController,
        ) {

            NavHost(
                navController, startDestination = LIST_SCREEN
            ) {
                composable(
                    LIST_SCREEN,
                    arguments = listOf(navArgument(LIST_ARGUMENT_KEY) {
                        type = NavType.StringType
                    })
                ) { backStackEntry ->
                    val action = backStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()

                    ListScreen(action, sharedViewModel)
                }
                composable(
                    TASK_SCREEN,
                    arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
                        type = NavType.IntType
                    })
                ) { backStackEntry ->
                    val taskId = backStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
                    TaskScreen(taskId = taskId, sharedViewModel)
                }
            }
        }
    }
}

fun NavHostController.navigateToListScreen(action: Action) {
    this.navigate("list/${action.name}") {
        popUpTo(LIST_SCREEN) { inclusive = true }
    }
}


fun NavHostController.navigateToTaskScreen(taskId: Int) {
    this.navigate("task/$taskId")
}