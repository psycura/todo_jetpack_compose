package com.example.to_docompose.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.ui.viewmodels.TaskViewModel
import com.example.to_docompose.util.Action

@Composable
fun TaskScreen(
    navigateToListScreen: (Action) -> Unit,
    selectedTask: ToDoTask?,
    vm: TaskViewModel
) {
    val title by vm.title
    val description by vm.description
    val priority by vm.priority
    val fieldsAreValid by remember { derivedStateOf { vm.fieldsAreValid } }
    val context = LocalContext.current



    Scaffold(
        topBar = {
            TaskAppBarr(
                navigateToListScreen = { action ->
                    if (action == Action.NO_ACTION) {
                        navigateToListScreen(action)
                    } else {

                        if (fieldsAreValid) {
                            vm.saveTask(action)
                            navigateToListScreen(action)
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


