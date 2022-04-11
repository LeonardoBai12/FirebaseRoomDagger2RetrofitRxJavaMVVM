package io.lb.firebaseexample.user_feature.presentation.sign_in

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
class SignInViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val useCases: UserUseCases
) : AndroidViewModel(context as Application) {
    private var typedName: String? = null
    private var typedEmail: String? = null
    private var typedPassword: String? = null
    private var typedRepeatPassword: String? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
        object SingIn : UiEvent()
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
                    typedRepeatPassword = event.value
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
                                insertUser(user)
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
    private fun insertUser(user: FirebaseUser) {
        CoroutineScope(Dispatchers.IO).launch {
            useCases.insertUserUseCase(user, typedName ?: "").addOnSuccessListener {
                emit(UiEvent.SingIn)
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