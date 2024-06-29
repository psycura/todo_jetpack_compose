package com.example.to_docompose.ui.screens.list

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.to_docompose.LocalNavController
import com.example.to_docompose.R
import com.example.to_docompose.navigation.destinations.navigateToTaskScreen
import com.example.to_docompose.ui.theme.fabBackgroundColor
import com.example.to_docompose.ui.theme.topAppBarContentColor
import com.example.to_docompose.ui.viewmodels.SharedViewModel
import com.example.to_docompose.util.Action
import com.example.to_docompose.util.SearchAppBarState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen(
    sharedViewModel: SharedViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = true) {
        Log.d("ListScreen", "LaunchedEffect Triggered")
        sharedViewModel.getAllTasks()
    }


    val allTasksRequest by sharedViewModel.allTasks.collectAsState()

    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    val navController = LocalNavController.current



    Scaffold(
        topBar = {
            ListAppBar(
                sharedViewModel,
                searchAppBarState = searchAppBarState,
                searchTextState = searchTextState
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                ListContent(allTasksRequest)
            }
        },
        floatingActionButton = {
            ListFab(onFabCLicked = {
                navController.navigateToTaskScreen(-1)
            })
        }
    )
}

@Composable
fun ListFab(
    onFabCLicked: () -> Unit
) {
    FloatingActionButton(
        onClick = { onFabCLicked() },
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
    handleDatabaseActions: () -> Unit,
    taskTitle: String,
    action: Action
) {
    handleDatabaseActions()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = snackbarHostState.showSnackbar(
                    message = "${action.name} : $taskTitle",
                    actionLabel = "OK"
                )
            }
        }
    }
}
