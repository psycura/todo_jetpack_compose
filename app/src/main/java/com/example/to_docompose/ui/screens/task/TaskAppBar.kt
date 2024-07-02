@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.to_docompose.ui.screens.task

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.R
import com.example.to_docompose.components.DisplayAlertDialog
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.ui.theme.topAppBarBackgroundColor
import com.example.to_docompose.ui.theme.topAppBarContentColor
import com.example.to_docompose.util.Action

@Composable
fun TaskAppBarr(
    navigateToListScreen: (Action) -> Unit,
    selectedTask: ToDoTask?,
) {
    if (selectedTask == null) {
        NewTaskAppBarr(navigateToListScreen = navigateToListScreen)
    } else {
        EditTaskAppBarr(
            navigateToListScreen = navigateToListScreen,
            task = selectedTask
        )
    }
}

@Composable
fun NewTaskAppBarr(
    navigateToListScreen: (Action) -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            BackAction(navigateToListScreen)
        },
        title = {
            Text(
                stringResource(R.string.add_task),
                color = MaterialTheme.colorScheme.topAppBarContentColor
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor,
        ),
        actions = {
            AddAction(navigateToListScreen)
        }
    )
}

@Composable
fun BackAction(onClick: (Action) -> Unit) {
    IconButton(onClick = { onClick(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_arrow),
            tint = MaterialTheme.colorScheme.topAppBarContentColor,
        )
    }
}

@Composable
fun AddAction(onClick: (Action) -> Unit) {
    IconButton(onClick = { onClick(Action.ADD) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.add_icon),
            tint = MaterialTheme.colorScheme.topAppBarContentColor,
        )
    }
}


@Composable
fun EditTaskAppBarr(
    navigateToListScreen: (Action) -> Unit,
    task: ToDoTask,
    ) {
    TopAppBar(
        navigationIcon = {
            CloseAction(navigateToListScreen)
        },
        title = {
            Text(
                task.title,
                color = MaterialTheme.colorScheme.topAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor,
        ),
        actions = { EditTaskAppBarActions(navigateToListScreen, task) }
    )
}

@Composable
fun EditTaskAppBarActions(
    navigateToListScreen: (Action) -> Unit,
    selectedTask: ToDoTask,
) {
    var openDialog by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { navigateToListScreen(Action.DELETE) },
        title = stringResource(R.string.delete_task_action, selectedTask.title),
        message = stringResource(R.string.delete_task_confirmation, selectedTask.title)
    )

    DeleteAction { openDialog = true }
    UpdateAction(navigateToListScreen)
}


@Composable
fun CloseAction(onClick: (Action) -> Unit) {
    IconButton(onClick = { onClick(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(R.string.close_search),
            tint = MaterialTheme.colorScheme.topAppBarContentColor,
        )
    }
}


@Composable
fun DeleteAction(onClick: () -> Unit) {
    IconButton({ onClick() }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.delete_task),
            tint = MaterialTheme.colorScheme.topAppBarContentColor,
        )
    }
}

@Composable
fun UpdateAction(onClick: (Action) -> Unit) {
    IconButton(onClick = { onClick(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.update_task),
            tint = MaterialTheme.colorScheme.topAppBarContentColor,
        )
    }
}


@Composable
@Preview
fun NewTaskAppBarrPreview() {
    NewTaskAppBarr(navigateToListScreen = {})
}

@Composable
@Preview
fun EditTaskAppBarrPreview() {
    EditTaskAppBarr(
        navigateToListScreen = {},
        task = ToDoTask(
            0,
            "Title",
            "Some random text",
            Priority.HIGH
        )
    )
}