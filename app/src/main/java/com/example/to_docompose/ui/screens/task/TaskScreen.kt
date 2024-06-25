package com.example.to_docompose.ui.screens.task

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.to_docompose.ui.viewmodels.SharedViewModel
import com.example.to_docompose.util.Action

@Composable
fun TaskScreen(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel,
    taskId: Int
) {
    Scaffold (
        topBar = {
            TaskAppBarr(navigateToListScreen = navigateToListScreen)
        },
        content = {it}
    )
}