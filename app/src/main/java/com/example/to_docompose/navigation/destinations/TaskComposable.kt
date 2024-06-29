package com.example.to_docompose.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.to_docompose.ui.screens.task.TaskScreen
import com.example.to_docompose.ui.viewmodels.SharedViewModel
import com.example.to_docompose.ui.viewmodels.TaskViewModel
import com.example.to_docompose.util.Action
import com.example.to_docompose.util.Constants.TASK_ARGUMENT_KEY
import com.example.to_docompose.util.Constants.TASK_SCREEN
import org.koin.androidx.compose.koinViewModel


fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel,
) {
    composable(
        TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)
        sharedViewModel.getSelectedTask(taskId)
        val selectedTask by sharedViewModel.selectedTask.collectAsState()
        val taskVm: TaskViewModel = koinViewModel()

        LaunchedEffect(key1 = selectedTask) {
            taskVm.initTask(selectedTask)
        }

        TaskScreen(
            navigateToListScreen = navigateToListScreen,
            selectedTask = selectedTask,
            vm = taskVm
        )
    }
}