package io.lb.firebaseexample.settings_feature.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.lb.firebaseexample.settings_feature.domain.use_case.SettingsUseCases
import io.lb.firebaseexample.util.DataStoreKeys
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    app: Application,
    private val useCases: SettingsUseCases
): AndroidViewModel(app) {
    val allowRestartTodo = MutableLiveData<Boolean?>()
    val showGreetings = MutableLiveData<Boolean?>()

    fun onEvent(event: SettingsEvent) {
        viewModelScope.launch {
            when (event) {
                is SettingsEvent.OnAllowRestartTodoSwitched -> {
                    useCases.saveSettingUseCase(DataStoreKeys.ALLOW_RESTART_TODO, event.value)
                }
                is SettingsEvent.OnShowGreetingsSwitched -> {
                    useCases.saveSettingUseCase(DataStoreKeys.SHOW_GREETINGS, event.value)
                }
            }
        }
    }

    fun getAllowRestartTodo() {
        CoroutineScope(Dispatchers.IO).launch {
            allowRestartTodo.postValue(
                useCases.getSettingsUseCase(DataStoreKeys.ALLOW_RESTART_TODO)
            )
        }
    }

    fun getShowGreetings() {
        CoroutineScope(Dispatchers.IO).launch {
            showGreetings.postValue(
                useCases.getSettingsUseCase(DataStoreKeys.SHOW_GREETINGS)
            )
        }
    }
}