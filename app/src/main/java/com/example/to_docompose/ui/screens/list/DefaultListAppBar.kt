@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.to_docompose.ui.screens.list


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.R
import com.example.to_docompose.components.DisplayAlertDialog
import com.example.to_docompose.components.DropdownPriorityItem
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.ui.theme.topAppBarBackgroundColor
import com.example.to_docompose.ui.theme.topAppBarContentColor


@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Tasks",
                color = MaterialTheme.colorScheme.topAppBarContentColor
            )
        },
        actions = {
            ListBarActions(
                onSearchClicked = onSearchClicked,
                onSortClicked = onSortClicked,
                onDeleteClick = onDeleteClick
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor,
        ),
    )
}

@Composable
fun ListBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClick: () -> Unit
) {

    var openDialog by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { onDeleteClick() },
        title = stringResource(R.string.delete_all_action),
        message = stringResource(R.string.delete_all_confirmation)
    )

    SearchAction(onSearchClicked)
    SortAction(onSortClicked)
    DeleteAllAction { openDialog = true }
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(onClick = { onSearchClicked() }) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_tasks),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = !expanded }) {
        Icon(
            painter = painterResource(id = R.drawable.filter_list),
            contentDescription = stringResource(R.string.sort_task),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownPriorityItem(
                priority = Priority.LOW,
                onClick = {
                    expanded = false
                    onSortClicked(it)
                }
            )
            DropdownPriorityItem(
                priority = Priority.MEDIUM,
                onClick = {
                    expanded = false
                    onSortClicked(it)
                }
            )
            DropdownPriorityItem(
                priority = Priority.HIGH,
                onClick = {
                    expanded = false
                    onSortClicked(it)
                }
            )
        }

    }
}

@Composable
fun DeleteAllAction(
    onDeleteClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = !expanded }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.open_menu),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onDeleteClick()
                },
                text = {
                    Text(
                        stringResource(R.string.delete_all),
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(R.string.delete_task),
                    )
                }
            )
        }
    }
}


@Composable
@Preview
private fun DefaultListAppBarPreview() {
    DefaultListAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteClick = {}
    )
}