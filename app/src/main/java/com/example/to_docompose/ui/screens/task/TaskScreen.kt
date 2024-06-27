package com.example.to_docompose.ui.screens.task

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.util.Action

@Composable
fun TaskScreen(
    navigateToListScreen: (Action) -> Unit,
    selectedTask: ToDoTask?,
) {
    Scaffold(
        topBar = {
            TaskAppBarr(
                navigateToListScreen = navigateToListScreen,
                selectedTask = selectedTask
            )
        },
        content = { it }
    )
}