package io.lb.firebaseexample.todo_feature.presentation.todo_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.lb.firebaseexample.todo_feature.domain.use_case.TodoUseCases
import io.lb.firebaseexample.user_feature.presentation.login.LoginViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoDetailsViewModel @Inject constructor(
    app: Application,
    private val useCases: TodoUseCases
): AndroidViewModel(app) {
    private var typedTitle: String? = null
    private var typedDescription: String? = null
    private var typedDate: String? = null
    private var typedDeadline: String? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowToast(val message: String): UiEvent()
        object Finish: UiEvent()
    }

    fun onEvent(event: TodoDetailsEvent) {
        viewModelScope.launch {
            when (event) {
                is TodoDetailsEvent.EnteredTitle -> {
                    typedTitle = event.value
                }
                is TodoDetailsEvent.EnteredDescription -> {
                    typedDescription = event.value
                }
                is TodoDetailsEvent.EnteredDate -> {
                    typedDate = event.value
                }
                is TodoDetailsEvent.EnteredDeadline -> {
                    typedDeadline = event.value
                }
                is TodoDetailsEvent.PressedFinish -> {
                    try {
                        useCases.saveTodoUseCase(
                            event.id,
                            typedTitle,
                            typedDescription,
                            typedDate,
                            typedDeadline
                        ).addOnSuccessListener {
                            emit(UiEvent.Finish)
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