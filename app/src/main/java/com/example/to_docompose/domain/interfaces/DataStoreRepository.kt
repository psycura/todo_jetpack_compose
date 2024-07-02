package com.example.to_docompose.domain.interfaces

import com.example.to_docompose.data.models.Priority
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveSortState(priority: Priority)
    fun readSortState(): Flow<Priority>
}