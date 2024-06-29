package com.example.to_docompose.di

import android.content.Context
import androidx.room.Room
import com.example.to_docompose.data.ToDoDao
import com.example.to_docompose.data.ToDoDatabase
import com.example.to_docompose.data.repositories.TodoRepositoryImpl
import com.example.to_docompose.domain.interfaces.TodoRepository
import com.example.to_docompose.ui.viewmodels.SharedViewModel
import com.example.to_docompose.ui.viewmodels.TaskViewModel
import com.example.to_docompose.util.Constants.DATABASE_NAME
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    single { provideRoomDatabase(get()) }
    single { provideDao(get()) }
    single<TodoRepository> { TodoRepositoryImpl(get()) }
    viewModel<SharedViewModel> { SharedViewModel(get()) }
    viewModel<TaskViewModel> {
        TaskViewModel(repository = get())
    }
}


fun provideRoomDatabase(context: Context): ToDoDatabase {
    println("KoinModule: provideRoomDatabase")
    val database: ToDoDatabase?
    database = Room.databaseBuilder(context, ToDoDatabase::class.java, DATABASE_NAME)
        .build()

    return database
}

private fun provideDao(database: ToDoDatabase): ToDoDao {
    return database.todoDao()
}
