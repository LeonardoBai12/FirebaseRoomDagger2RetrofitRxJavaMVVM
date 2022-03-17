package io.lb.firebaseexample.user_feature.presentation.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.lb.firebaseexample.user_feature.domain.use_case.UserUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    app: Application,
    private val useCases: UserUseCases
) : AndroidViewModel(app) {
    private var typedEmail: String? = null
    private var typedPassword: String? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowToast(val message: String): UiEvent()
        object Login: UiEvent()
        object OpenSignInScreen: UiEvent()
    }

    fun onEvent(event: LoginEvent) {
        viewModelScope.launch {
            when (event) {
                is LoginEvent.EnteredEmail -> {
                    typedEmail = event.value
                }
                is LoginEvent.EnteredPassword -> {
                    typedPassword = event.value
                }
                is LoginEvent.PressedLogin -> {
                    try {
                        useCases.loginUserUseCase(typedEmail, typedPassword).addOnSuccessListener {
                            emit(UiEvent.Login)
                        }.addOnFailureListener {
                            emit(exceptionToast(it))
                        }
                    } catch (e: Exception) {
                        _eventFlow.emit(exceptionToast(e))
                    }
                }
                is LoginEvent.PressedSignIn -> {
                    emit(UiEvent.OpenSignInScreen)
                }
            }
        }
    }

    private fun exceptionToast(e: Exception) =
        UiEvent.ShowToast(
            message = e.message ?: "Something went wrong!"
        )

    private fun emit(event: UiEvent) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }
}