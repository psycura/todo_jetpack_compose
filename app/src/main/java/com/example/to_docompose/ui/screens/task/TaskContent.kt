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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.to_docompose.R
import com.example.to_docompose.components.PriorityDropdown
import com.example.to_docompose.ui.theme.LARGE_PADDING
import com.example.to_docompose.ui.theme.MEDIUM_PADDING
import com.example.to_docompose.ui.viewmodels.TaskViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TaskContent(
    modifier: Modifier = Modifier,
    vm: TaskViewModel = koinViewModel()
) {

    val title by vm.title
    val description by vm.description
    val priority by vm.priority

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(all = LARGE_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { vm.onTitleChanged(it) },
            placeholder = { Text(text = stringResource(R.string.title)) },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
            shape = MaterialTheme.shapes.small
        )
        PriorityDropdown(
            priority = priority,
            onPrioritySelected = { vm.onPriorityChanged(it) },
            modifier = Modifier.padding(vertical = MEDIUM_PADDING)
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = description,
            onValueChange = { vm.onDescriptionChanged(it) },
            placeholder = { Text(text = stringResource(R.string.description)) },
            textStyle = MaterialTheme.typography.bodyLarge,
            shape = MaterialTheme.shapes.small
        )
    }

}

