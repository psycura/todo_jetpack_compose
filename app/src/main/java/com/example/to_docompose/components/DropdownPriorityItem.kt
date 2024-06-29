package com.example.to_docompose.components

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.to_docompose.data.models.Priority

@Composable
fun DropdownPriorityItem(
    priority: Priority,
    onClick: (Priority) -> Unit
) {
    DropdownMenuItem(
        text = { Text(priority.name) },
        onClick = { onClick(priority) },
        leadingIcon = {
            PriorityItem(priority = priority)
        }

    )
}