package com.example.to_docompose.ui.screens.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.data.models.Priority
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
        content = { innerPadding ->
            TaskContent(
                title = selectedTask?.title ?: "",
                onTitleChange = {},
                description = selectedTask?.description ?: "",
                onDescriptionChange = {},
                priority = selectedTask?.priority ?: Priority.LOW,
                onPrioritySelected = {},
                modifier = Modifier.padding(innerPadding)
            )
        }
    )
}

@Composable
@Preview
fun TaskScreenPreview() {
    TaskScreen(
        navigateToListScreen = {},
        selectedTask = null
    )

}