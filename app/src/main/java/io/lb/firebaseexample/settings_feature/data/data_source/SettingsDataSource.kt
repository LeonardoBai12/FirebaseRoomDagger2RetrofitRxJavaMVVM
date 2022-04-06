package io.lb.firebaseexample.settings_feature.data.data_source

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class SettingsDataSource(
    private val context: Application
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    suspend fun save(
        key: String,
        value: Boolean
    ) {
        val dataStoreKey = booleanPreferencesKey(key)
        context.dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun read(key: String): Boolean {
        val dataStoreKey = booleanPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[dataStoreKey] ?: false
    }
}