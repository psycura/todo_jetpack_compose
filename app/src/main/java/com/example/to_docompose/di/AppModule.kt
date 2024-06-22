package com.example.to_docompose.di

import android.content.Context
import androidx.room.Room
import com.example.to_docompose.data.ToDoDao
import com.example.to_docompose.data.ToDoDatabase
import com.example.to_docompose.data.repositories.ToDoRepository
import com.example.to_docompose.util.Constants.DATABASE_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val appModule = module {
    single { provideRoomDatabase(androidContext()) }
    single { provideDao(get()) }
    single<ToDoRepository> { ToDoRepository(get()) }
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
