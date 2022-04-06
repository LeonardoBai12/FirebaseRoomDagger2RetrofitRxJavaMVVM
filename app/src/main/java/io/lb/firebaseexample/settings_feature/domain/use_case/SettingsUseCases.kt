package io.lb.firebaseexample.settings_feature.domain.use_case

data class SettingsUseCases(
    val getSettingsUseCase: GetSettingAccordingToKeyUseCase,
    val saveSettingUseCase: SaveSettingAccordingToKeyUseCase
)