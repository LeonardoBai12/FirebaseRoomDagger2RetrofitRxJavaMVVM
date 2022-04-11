package io.lb.firebaseexample.user_feature.presentation.login

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.lb.firebaseexample.user_feature.domain.use_case.UserUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val useCases: UserUseCases
) : AndroidViewModel(context as Application) {
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
                            it.user?.let { user ->
                                insertUser(user)
                            }
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

    private fun insertUser(user: FirebaseUser) {
        CoroutineScope(Dispatchers.IO).launch {
            val name = useCases.getUserUseCase(user).name
            useCases.insertUserUseCase(user, name ?: "").addOnSuccessListener {
                emit(UiEvent.Login)
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