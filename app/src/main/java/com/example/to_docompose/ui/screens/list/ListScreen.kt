package com.example.to_docompose.ui.screens.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.to_docompose.LocalNavController
import com.example.to_docompose.R
import com.example.to_docompose.navigateToTaskScreen
import com.example.to_docompose.ui.theme.fabBackgroundColor
import com.example.to_docompose.ui.theme.topAppBarContentColor
import com.example.to_docompose.ui.viewmodels.SharedViewModel
import com.example.to_docompose.util.Action
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    action: Action,
    sharedViewModel: SharedViewModel
) {

    val allTasksRequest by sharedViewModel.allTasks.collectAsState()

    val navController = LocalNavController.current
    val editedTask by sharedViewModel.editedTask
    val snackbarHostState = remember { SnackbarHostState() }

    DisplaySnackBar(
        snackbarHostState = snackbarHostState,
        taskTitle = editedTask?.title ?: "",
        action = action,
        onUndoClicked = { sharedViewModel.handleDbAction(it) }
    )


    LaunchedEffect(key1 = action) {
        sharedViewModel.handleDbAction(action)
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { ListAppBar(sharedViewModel) },
        floatingActionButton = {
            ListFab(onClick = {
                navController.navigateToTaskScreen(-1)
            })
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                ListContent(allTasksRequest)
            }
        }
    )
}

@Composable
fun ListFab(
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_button),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun DisplaySnackBar(
    snackbarHostState: SnackbarHostState,
    taskTitle: String,
    action: Action,
    onUndoClicked: (Action) -> Unit
) {

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = snackbarHostState.showSnackbar(
                    message = "${action.name} : $taskTitle",
                    duration = SnackbarDuration.Short,
                    actionLabel = setActionLabel(action)
                )
                undoDeletedTask(
                    action,
                    snackBarResult,
                    onUndoClicked = { onUndoClicked(it) }
                )
            }
        }
    }
}

private fun setActionLabel(action: Action): String {
    return if (action == Action.DELETE) {
        "UNDO"
    } else {
        "OK"
    }
}

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (
        snackBarResult == SnackbarResult.ActionPerformed
        && action == Action.DELETE
    ) {
        onUndoClicked(Action.UNDO)
    }
}
