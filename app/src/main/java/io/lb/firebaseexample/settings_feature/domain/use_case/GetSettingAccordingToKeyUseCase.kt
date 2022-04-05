package io.lb.firebaseexample.settings_feature.domain.use_case

import io.lb.firebaseexample.settings_feature.domain.repository.SettingsRepository

class GetSettingAccordingToKeyUseCase(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(key: String): Boolean {
        return repository.readSettingsAccordingToKey(key)
    }
}