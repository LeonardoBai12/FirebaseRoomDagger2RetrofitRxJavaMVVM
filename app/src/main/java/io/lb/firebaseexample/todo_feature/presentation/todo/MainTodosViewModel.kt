package io.lb.firebaseexample.todo_feature.presentation.todo

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.todo_feature.domain.use_case.TodoUseCases
import io.lb.firebaseexample.user_feature.domain.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainTodosViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val useCases: TodoUseCases
): AndroidViewModel(context as Application) {
    val todos = MutableLiveData<List<Todo>?>()
    val user = MutableLiveData<User?>()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class OnTodoClicked(val id: Int, val todo: Todo): UiEvent()
        object OnPressedAdd: UiEvent()
        object OnLogoutSuccess: UiEvent()
    }

    fun onEvent(event: MainTodosEvent) {
        viewModelScope.launch {
            when (event) {
                is MainTodosEvent.OnTodoClicked -> {
                    _eventFlow.emit(UiEvent.OnTodoClicked(event.id, event.todo))
                }
                is MainTodosEvent.OnTodoCheckClicked -> {
                    useCases.updateTodo(event.id, event.todo)
                }
                is MainTodosEvent.PressedLogout -> {
                    useCases.logoutUseCase()
                    _eventFlow.emit(UiEvent.OnLogoutSuccess)
                }
                is MainTodosEvent.PressedAdd -> {
                    _eventFlow.emit(UiEvent.OnPressedAdd)
                }
            }
        }
    }

    fun getTodos() {
        CoroutineScope(Dispatchers.IO).launch {
            useCases.getTodosUseCase().collectLatest {
                todos.postValue(it)
            }
        }
    }

    fun getUser() {
        CoroutineScope(Dispatchers.IO).launch {
            user.postValue(useCases.getUserUseCase())
        }
    }
}