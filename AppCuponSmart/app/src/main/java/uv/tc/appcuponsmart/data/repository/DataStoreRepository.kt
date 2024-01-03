package uv.tc.appcuponsmart.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import uv.tc.appcuponsmart.di.Constantes
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = Constantes.DataStore.ALMACENAMIENTO)

class DataStoreRepository @Inject constructor(
    @ApplicationContext
    private val context: Context
){
    fun getInt(key: String) = context.dataStore.data.map{ preferences ->
        preferences[intPreferencesKey(key)] ?: 0
    }

    suspend fun putInt(key: String, value: Int) = context.dataStore.edit{ preferences ->
        preferences[intPreferencesKey(key)] = value
    }

    fun getString(key: String) = context.dataStore.data.map{ preferences ->
        preferences[stringPreferencesKey(key)] ?: ""
    }

    suspend fun putString(key: String, value: String) = context.dataStore.edit{ preferences ->
        preferences[stringPreferencesKey(key)] = value
    }
}