package com.example.to_docompose.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.to_docompose.data.models.Priority
import com.example.to_docompose.domain.interfaces.DataStoreRepository
import com.example.to_docompose.util.Constants.PREFERENCE_KEY
import com.example.to_docompose.util.Constants.PREFERENCE_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCE_NAME)


class DataStoreRepositoryImpl(context: Context) : DataStoreRepository {

    private object PreferencesKeys {
        val sortKey = stringPreferencesKey(PREFERENCE_KEY)
    }

    private val dataStore = context.dataStore


    override suspend fun saveSortState(priority: Priority) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.sortKey] = priority.name
        }
    }

    override fun readSortState(): Flow<Priority> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    throw exception
                }
            }
            .map { preferences ->
                val sortState = preferences[PreferencesKeys.sortKey] ?: Priority.NONE.name
                Priority.valueOf(sortState)
            }

    }
}
