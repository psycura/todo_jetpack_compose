package com.example.to_docompose.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.domain.interfaces.DataStoreRepository
import com.example.to_docompose.domain.interfaces.TodoRepository
import com.example.to_docompose.util.Action
import com.example.to_docompose.util.RequestState
import com.example.to_docompose.util.SearchAppBarState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SharedViewModel(
    private val repository: TodoRepository,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {

    private val _searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)
    val searchAppBarState: State<SearchAppBarState> = _searchAppBarState

    private val _searchTextState: MutableState<String> = mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    private val _allTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    private val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks

    private val _searchedTasks = MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    private val searchedTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchedTasks

    private val _selectedTask = MutableStateFlow<ToDoTask?>(null)
    val selectedTask: StateFlow<ToDoTask?> = _selectedTask

    private val _editedTask: MutableState<ToDoTask?> = mutableStateOf(null)
    val editedTask: State<ToDoTask?> = _editedTask

    private val _action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)
    val sortState: StateFlow<RequestState<Priority>> = _sortState

    val tasksToDisplay: StateFlow<RequestState<List<ToDoTask>>>
        get() = if (_searchAppBarState.value == SearchAppBarState.TRIGGERED && searchedTasks.value is RequestState.Success)
            searchedTasks
        else
            allTasks

    init {
        getAllTasks()
        getSortState()
    }

    fun persistSortingState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveSortState(priority)
        }
    }

    private fun getSortState() {
        _sortState.value = RequestState.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                dataStoreRepository.readSortState().collect {
                    _sortState.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _sortState.value = RequestState.Error(e)
        }

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

    fun searchDb(searchQuery: String) {
        _searchedTasks.value = RequestState.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {

                repository.searchTasks(searchQuery).collect {

                    _searchedTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {

            _searchedTasks.value = RequestState.Error(e)
        }

        _searchAppBarState.value = SearchAppBarState.TRIGGERED

    }


    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {

            repository.getSelectedTask(taskId).collect { task ->
                _selectedTask.value = task
            }
        }
    }

    fun setEditedTask(task: ToDoTask?) {
        _editedTask.value = task
    }

    fun handleDbAction(action: Action) {
        _action.value = action

        when (action) {
            Action.DELETE -> deleteTask(editedTask.value!!)
            Action.DELETE_ALL -> deleteAllTasks()
            Action.UNDO -> undoDeleteTask()
            else -> {
                _selectedTask.value = null
                _editedTask.value = null
            }
        }
    }

    private fun undoDeleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(editedTask.value!!)
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

            SearchAppBarState.TRIGGERED -> {
                _searchAppBarState.value = SearchAppBarState.CLOSED
                _searchTextState.value = ""
                _searchedTasks.value = RequestState.Idle
            }

        }
    }

    fun resetAppBarState() {
        _searchAppBarState.value = SearchAppBarState.CLOSED
        _searchTextState.value = ""
        _searchedTasks.value = RequestState.Idle
    }

    fun onSearchTextChanged(text: String) {
        _searchTextState.value = text
    }


    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

    private fun deleteTask(task: ToDoTask) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
        _selectedTask.value = null
    }

}