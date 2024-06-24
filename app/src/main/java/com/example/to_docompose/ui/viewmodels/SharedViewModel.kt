package com.example.to_docompose.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.domain.interfaces.TodoRepository
import com.example.to_docompose.util.SearchAppBarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedViewModel(private val repository: TodoRepository) : ViewModel() {

    private val _searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
    val searchAppBarState: State<SearchAppBarState> = _searchAppBarState

    private val _searchTextState: MutableState<String> = mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    private val _allTasks = MutableStateFlow<List<ToDoTask>>(emptyList())
    val allTasks: StateFlow<List<ToDoTask>> = _allTasks

    fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks.collect {
                _allTasks.value = it
            }
        }
    }

    fun toggleAppBarState() {
        when (_searchAppBarState.value) {
            SearchAppBarState.CLOSED -> _searchAppBarState.value = SearchAppBarState.OPENED
            SearchAppBarState.OPENED -> {
                if (_searchTextState.value.isEmpty()) {
                    _searchAppBarState.value = SearchAppBarState.CLOSED
                } else {
                    _searchTextState.value = ""
                }
            }

            else -> {}
//            SearchAppBarState.TRIGGERED -> _searchAppBarState.value = SearchAppBarState.CLOSED
        }
    }

    fun onSearchTextChanged(text: String) {
        _searchTextState.value = text
    }
}