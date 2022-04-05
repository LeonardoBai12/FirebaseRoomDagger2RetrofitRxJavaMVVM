package io.lb.firebaseexample.settings_feature.data.repository

import io.lb.firebaseexample.settings_feature.data.data_source.SettingsDataSource
import io.lb.firebaseexample.settings_feature.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val dataSource: SettingsDataSource
): SettingsRepository {
    override suspend fun saveSettingsAccordingToKey(key: String, value: Boolean) {
        dataSource.save(key, value)
    }

    override suspend fun readSettingsAccordingToKey(key: String): Boolean {
        return dataSource.read(key)
    }
}