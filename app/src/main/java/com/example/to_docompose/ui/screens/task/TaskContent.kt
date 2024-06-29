package com.example.to_docompose.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.R
import com.example.to_docompose.components.PriorityDropdown
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.ui.theme.LARGE_PADDING
import com.example.to_docompose.ui.theme.MEDIUM_PADDING

@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(all = LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChange(it) },
            placeholder = { Text(text = stringResource(R.string.title)) },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
            shape = MaterialTheme.shapes.small
        )
        PriorityDropdown(
            priority = priority,
            onPrioritySelected = onPrioritySelected,
            modifier = Modifier.padding(vertical = MEDIUM_PADDING)
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { onDescriptionChange(it) },
            placeholder = { Text(text = stringResource(R.string.description)) },
            textStyle = MaterialTheme.typography.bodyLarge,
            shape = MaterialTheme.shapes.small
        )
    }

}

@Composable
@Preview()
fun TaskContentPreview() {
    TaskContent(
        title = "Title",
        onTitleChange = {},
        description = "Description",
        onDescriptionChange = {},
        priority = Priority.LOW,
        onPrioritySelected = {}
    )
}