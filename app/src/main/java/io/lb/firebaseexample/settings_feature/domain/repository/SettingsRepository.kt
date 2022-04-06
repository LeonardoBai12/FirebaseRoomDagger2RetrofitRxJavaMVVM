package io.lb.firebaseexample.settings_feature.domain.repository

interface SettingsRepository {
    suspend fun saveSettingsAccordingToKey(key: String, value: Boolean)
    suspend fun readSettingsAccordingToKey(key: String): Boolean
}