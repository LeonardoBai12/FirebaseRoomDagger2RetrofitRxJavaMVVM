package io.lb.firebaseexample.settings_feature.domain.use_case

import io.lb.firebaseexample.settings_feature.domain.repository.SettingsRepository

class SaveSettingAccordingToKeyUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(key: String, value: Boolean) {
        return repository.saveSettingsAccordingToKey(key, value)
    }
}