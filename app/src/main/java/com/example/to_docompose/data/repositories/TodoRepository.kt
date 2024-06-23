package com.example.to_docompose.data.repositories

import com.example.to_docompose.data.ToDoDao
import com.example.to_docompose.data.models.ToDoTask
import com.example.to_docompose.domain.interfaces.TodoRepository
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val dao: ToDoDao) : TodoRepository {

    override val getAllTasks: Flow<List<ToDoTask>> = dao.getAllTasks()
    override val sortByLowPriority: Flow<List<ToDoTask>> = dao.sortByLowPriority()
    override val sortByHighPriority: Flow<List<ToDoTask>> = dao.sortByHighPriority()

    override fun getSelectedTask(taskId: Int): Flow<ToDoTask> {
        return dao.getSelectedTask(taskId)
    }

    override suspend fun addTask(toDoTask: ToDoTask) {
        dao.insertTask(toDoTask)
    }

    override suspend fun updateTask(toDoTask: ToDoTask) {
        dao.updateTask(toDoTask)
    }

    override suspend fun deleteTask(toDoTask: ToDoTask) {
        dao.deleteTask(toDoTask)
    }

    override suspend fun deleteAllTasks() {
        dao.deleteAllTasks()
    }

    override fun searchTasks(searchQuery: String): Flow<List<ToDoTask>> {
        return dao.searchDatabase(searchQuery)
    }

}