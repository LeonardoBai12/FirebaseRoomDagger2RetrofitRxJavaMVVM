package io.lb.firebaseexample.settings_feature.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.lb.firebaseexample.settings_feature.domain.use_case.SettingsUseCases
import io.lb.firebaseexample.util.DataStoreKeys
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    app: Application,
    private val useCases: SettingsUseCases
): AndroidViewModel(app) {
    private var typedName: String? = null

    fun onEvent(event: SettingsEvent) {
        viewModelScope.launch {
            when (event) {
                is SettingsEvent.EnteredName -> {
                    typedName = event.value
                }
                is SettingsEvent.OnAllowRestartTodoSwitched -> {
                    useCases.saveSettingUseCase(DataStoreKeys.ALLOW_RESTART_TODO, event.value)
                }
                is SettingsEvent.OnShowGreetingsSwitched -> {
                    useCases.saveSettingUseCase(DataStoreKeys.SHOW_GREETINGS, event.value)
                }
            }
        }
    }

    suspend fun getSetting(key: String): Boolean {
        return useCases.getSettingsUseCase(key)
    }
}