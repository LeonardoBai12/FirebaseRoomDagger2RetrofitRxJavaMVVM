package io.lb.firebaseexample.settings_feature.presentation

sealed class SettingsEvent {
    data class EnteredName(val value: String): SettingsEvent()
    data class OnAllowRestartTodoSwitched(val value: Boolean): SettingsEvent()
    data class OnShowGreetingsSwitched(val value: Boolean): SettingsEvent()
}