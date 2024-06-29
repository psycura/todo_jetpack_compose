package com.example.to_docompose.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.domain.interfaces.TodoRepository
import com.example.to_docompose.util.Action
import com.example.to_docompose.util.RequestState
import com.example.to_docompose.util.SearchAppBarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedViewModel(private val repository: TodoRepository) : ViewModel() {

    private val _searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
    val searchAppBarState: State<SearchAppBarState> = _searchAppBarState

    private val _searchTextState: MutableState<String> = mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    private val _selectedTask = MutableStateFlow<ToDoTask?>(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    private val _editedTask: MutableState<ToDoTask?> = mutableStateOf(null)
    val editedTask: State<ToDoTask?> = _editedTask

    private val _action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)
    val action: State<Action> = _action

    init {
        Log.d("Alitz", "SharedViewModel init")
        getAllTasks()
    }


    private fun getAllTasks() {
        _allTasks.value = RequestState.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getAllTasks.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }

    }

    fun getSelectedTask(taskId: Int) {
        println("Alitz getSelectedTask #1 taskId:$taskId")
        viewModelScope.launch {
            println("Alitz getSelectedTask #2 taskId:$taskId")

            repository.getSelectedTask(taskId).collect { task ->
                println("Alitz getSelectedTask #3 taskId:$taskId")

                _selectedTask.value = task
            }
        }
    }

    fun setEditedTask(task: ToDoTask?) {
        _editedTask.value = task
    }

    fun setAction(action: Action) {
        _action.value = action
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


    fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

    fun deleteTask(task: ToDoTask) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

}