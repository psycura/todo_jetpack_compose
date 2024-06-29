package com.example.to_docompose.ui.screens.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.to_docompose.ui.viewmodels.SharedViewModel
import com.example.to_docompose.util.SearchAppBarState

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
) {

    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    when (searchAppBarState) {

        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = { sharedViewModel.toggleAppBarState() },
                onSortClicked = {},
                onDeleteClick = {}
            )
        }

        else -> {
            SearchListAppBar(
                text = searchTextState,
                onTextChanged = { sharedViewModel.onSearchTextChanged(it) },
                onSearchClicked = { },
                onCloseClicked = { sharedViewModel.toggleAppBarState() }
            )
        }
    }

}