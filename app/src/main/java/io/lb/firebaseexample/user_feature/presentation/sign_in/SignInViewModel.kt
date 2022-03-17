package io.lb.firebaseexample.user_feature.presentation.sign_in

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.lb.firebaseexample.user_feature.domain.use_case.UserUseCases
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    app: Application,
    private val useCases: UserUseCases
) : AndroidViewModel(app) {
    private var typedName: String? = null
    private var typedEmail: String? = null
    private var typedPassword: String? = null
    private var typedRepeatPassword: String? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowToast(val message: String): UiEvent()
        object SingIn: UiEvent()
    }

    fun onEvent(event: SignInEvent) {
        viewModelScope.launch {
            when (event) {
                is SignInEvent.EnteredEmail -> {
                    typedEmail = event.value
                }
                is SignInEvent.EnteredPassword -> {
                    typedPassword = event.value
                }
                is SignInEvent.EnteredRepeatPassword -> {
                    typedPassword = event.value
                }
                is SignInEvent.EnteredName -> {
                    typedName = event.value
                }
                is SignInEvent.PressedSignIn -> {
                    try {
                        useCases.createUserUseCase(
                            typedEmail,
                            typedPassword,
                            typedRepeatPassword
                        ).addOnSuccessListener {
                            it.user?.let { user ->
                                useCases.insertUserUseCase(user, typedName ?: "")
                                emit(UiEvent.SingIn)
                            }
                        }.addOnFailureListener {
                            emit(exceptionToast(it))
                        }
                    } catch (e: Exception) {
                        _eventFlow.emit(exceptionToast(e))
                    }
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