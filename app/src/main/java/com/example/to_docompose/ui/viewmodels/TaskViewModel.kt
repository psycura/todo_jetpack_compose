package com.example.to_docompose.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.domain.interfaces.TodoRepository
import com.example.to_docompose.util.Action
import com.example.to_docompose.util.Constants.MAX_TITLE_LENGTH
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TodoRepository) : ViewModel() {
    fun initTask(task: ToDoTask?) {
        _id.value = task?.id ?: 0
        _title.value = task?.title ?: ""
        _description.value = task?.description ?: ""
        _priority.value = task?.priority ?: Priority.LOW
    }

    private val _id: MutableState<Int> = mutableIntStateOf(0)

    private val _title: MutableState<String> = mutableStateOf("")
    val title: State<String> = _title

    private val _description: MutableState<String> = mutableStateOf("")
    val description: State<String> = _description

    private val _priority: MutableState<Priority> = mutableStateOf(Priority.LOW)
    val priority: State<Priority> = _priority

    val fieldsAreValid: Boolean
        get() = _title.value.isNotEmpty() && _description.value.isNotEmpty()

    fun onTitleChanged(newTitle: String) {
        if (newTitle.length <= MAX_TITLE_LENGTH) {
            _title.value = newTitle
        }
    }

    fun onDescriptionChanged(description: String) {
        _description.value = description
    }

    fun onPriorityChanged(newPriority: Priority) {
        _priority.value = newPriority
    }


    fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(
                ToDoTask(
                    id = _id.value,
                    title = title.value,
                    description = description.value,
                    priority = priority.value
                )
            )
        }
    }

    fun crateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(
                ToDoTask(
                    title = title.value,
                    description = description.value,
                    priority = priority.value
                )
            )
        }
    }

    fun saveTask(action: Action) {
        when (action) {
            Action.ADD -> crateTask()
            Action.UPDATE -> updateTask()
            else -> {}
        }
    }

}