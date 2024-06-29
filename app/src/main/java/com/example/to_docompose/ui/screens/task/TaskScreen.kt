package com.example.to_docompose.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.to_docompose.LocalNavController
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.navigation.destinations.navigateToListScreen
import com.example.to_docompose.ui.viewmodels.TaskViewModel
import com.example.to_docompose.util.Action
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    vm: TaskViewModel = koinViewModel()
) {

    val title by vm.title
    val description by vm.description
    val priority by vm.priority
    val fieldsAreValid by remember { derivedStateOf { vm.fieldsAreValid } }
    val context = LocalContext.current

    val navController = LocalNavController.current


    LaunchedEffect(key1 = selectedTask) {
        vm.initTask(selectedTask)
    }

    Scaffold(
        topBar = {
            TaskAppBarr(
                navigateToListScreen = { action ->
                    if (action == Action.NO_ACTION) {
                        navController.navigateToListScreen(action)
                    } else {

                        if (fieldsAreValid) {
                            vm.saveTask(action)
                            navController.navigateToListScreen(action)
                        } else {
                            displayToast(context)
                        }
                    }

                },
                selectedTask = selectedTask
            )
        },
        content = { innerPadding ->
            TaskContent(
                title = title,
                onTitleChange = { vm.onTitleChanged(it) },
                description = description,
                onDescriptionChange = { vm.onDescriptionChanged(it) },
                priority = priority,
                onPrioritySelected = { vm.onPriorityChanged(it) },
                modifier = Modifier.padding(innerPadding)
            )
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


