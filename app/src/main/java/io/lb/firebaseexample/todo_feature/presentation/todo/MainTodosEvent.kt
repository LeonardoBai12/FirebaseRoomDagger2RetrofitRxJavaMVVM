package io.lb.firebaseexample.todo_feature.presentation.todo

import io.lb.firebaseexample.todo_feature.domain.model.Todo

sealed class MainTodosEvent {
    data class OnTodoClicked(val id: Int, val todo: Todo): MainTodosEvent()
    object PressedAdd : MainTodosEvent()
    object PressedLogout : MainTodosEvent()
    object PressedSettings : MainTodosEvent()
}