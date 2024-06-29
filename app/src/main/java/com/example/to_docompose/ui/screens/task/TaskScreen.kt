package com.example.to_docompose.ui.screens.task

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.to_docompose.LocalNavController
import com.example.to_docompose.navigateToListScreen
import com.example.to_docompose.ui.viewmodels.SharedViewModel

import com.example.to_docompose.ui.viewmodels.TaskViewModel
import com.example.to_docompose.util.Action
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskScreen(
    taskId: Int,
    sharedViewModel: SharedViewModel,
    taskViewModel: TaskViewModel = koinViewModel(),
) {

    LaunchedEffect(key1 = taskId) {
        sharedViewModel.getSelectedTask(taskId)
    }

    val selectedTask by sharedViewModel.selectedTask.collectAsState()

    val fieldsAreValid by remember { derivedStateOf { taskViewModel.fieldsAreValid } }
    val context = LocalContext.current

    val navController = LocalNavController.current

    fun handleNavActions(
        action: Action
    ) {
        if (action == Action.NO_ACTION) {
            navController.navigateToListScreen(action)
        } else {

            if (fieldsAreValid) {
                val task = taskViewModel.saveTask(action)
                Log.d("Alitz", "Task to save: $task")
                sharedViewModel.setEditedTask(task)
                navController.navigateToListScreen(action)
            } else {
                displayToast(context)
            }
        }
    }


    LaunchedEffect(key1 = selectedTask) {
        taskViewModel.initTask(selectedTask)
    }

    Scaffold(
        topBar = {
            TaskAppBarr(
                navigateToListScreen = { handleNavActions(it) },
                selectedTask = selectedTask
            )
        },
        content = { innerPadding ->
            TaskContent(modifier = Modifier.padding(innerPadding))
        }
    )
}

fun displayToast(context: Context) {
    Toast.makeText(
        context,
        "Fields are Empty",
        Toast.LENGTH_SHORT
    ).show()
}


