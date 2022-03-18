package io.lb.firebaseexample.todo_feature.presentation.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.todo_feature.domain.use_case.TodoUseCases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainTodosViewModel(
    app: Application,
    private val useCases: TodoUseCases
): AndroidViewModel(app) {
    val todos = MutableLiveData<List<Todo>>()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowToast(val message: String): UiEvent()
        data class OnTodoClicked(val id: Int, val todo: Todo): UiEvent()
        object OnPressedAdd: UiEvent()
        object OnPressedSettings: UiEvent()
        object OnLogoutSuccess: UiEvent()
    }

    fun onEvent(event: MainTodosEvent) {
        viewModelScope.launch {
            when (event) {
                is MainTodosEvent.OnTodoClicked -> {
                    _eventFlow.emit(UiEvent.OnTodoClicked(event.id, event.todo))
                }
                is MainTodosEvent.PressedLogout -> {
                    useCases.logoutUseCase()
                    _eventFlow.emit(UiEvent.OnLogoutSuccess)
                }
                is MainTodosEvent.PressedAdd -> {
                    _eventFlow.emit(UiEvent.OnPressedAdd)
                }
                is MainTodosEvent.PressedSettings -> {
                    _eventFlow.emit(UiEvent.OnPressedSettings)
                }
            }
        }
    }

    fun getHeadsets() {
        CoroutineScope(Dispatchers.IO).launch {
            todos.postValue(useCases.getTodosUseCase())
        }
    }

    private fun emitToast(message: String) {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.ShowToast(message))
        }
    }
}