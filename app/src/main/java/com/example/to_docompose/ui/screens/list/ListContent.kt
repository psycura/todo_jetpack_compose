@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.to_docompose.ui.screens.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.to_docompose.LocalNavController
import com.example.to_docompose.R
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.navigateToTaskScreen
import com.example.to_docompose.ui.theme.HighPriorityColor
import com.example.to_docompose.ui.theme.LARGEST_PADDING
import com.example.to_docompose.ui.theme.LARGE_PADDING
import com.example.to_docompose.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.to_docompose.ui.theme.taskItemBackgroundColor
import com.example.to_docompose.ui.theme.taskItemTextColor
import com.example.to_docompose.util.RequestState

@Composable
fun ListContent(
    tasks: RequestState<List<ToDoTask>>,
    onSwipeToDismiss: (task: ToDoTask) -> Unit
) {
    if (tasks is RequestState.Success) {
        if (tasks.data.isEmpty()) {
            EmptyContent()
        } else {
            LazyColumn {
                items(
                    items = tasks.data,
                    key = { task -> task.id }
                ) { task ->
                    val dismissState = rememberSwipeToDismissBoxState()

                    val degrees by animateFloatAsState(
                        label = "rotation degrees",
                        targetValue = if (dismissState.progress < 0.5f) 0f else -45f
                    )

                    val isDismissed =
                        dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart
                                && dismissState.progress == 1f

                    if (isDismissed) {
                        onSwipeToDismiss(task)
                    }


                    SwipeToDismissBox(
                        state = dismissState,
                        backgroundContent = { RedBackground(degrees) },
                        enableDismissFromEndToStart = true,
                        enableDismissFromStartToEnd = false,
                    ) {
                        TaskItem(task)
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    toDoTask: ToDoTask,
) {
    val navController = LocalNavController.current

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.taskItemBackgroundColor,
        shape = RectangleShape,
        shadowElevation = 2.dp,
        onClick = { navController.navigateToTaskScreen(toDoTask.id) }
    ) {
        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = toDoTask.title,
                    color = MaterialTheme.colorScheme.taskItemTextColor,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier.size(PRIORITY_INDICATOR_SIZE)
                    ) {
                        drawCircle(
                            color = toDoTask.priority.color
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = toDoTask.description,
                color = MaterialTheme.colorScheme.taskItemTextColor,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor)
            .padding(horizontal = LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            modifier = Modifier.rotate(degrees = degrees),
            contentDescription = stringResource(R.string.delete_task),
            tint = Color.White
        )
    }
}

@Composable
@Preview
private fun TaskItemPreview() {
    TaskItem(
        toDoTask = ToDoTask(0, "Test TODO", "test description", Priority.LOW),
    )
}