package io.lb.firebaseexample.todo_feature.presentation.todo_details

sealed class TodoDetailsEvent {
    data class PressedFinish(val id: Int): TodoDetailsEvent()
    data class EnteredTitle(val value: String): TodoDetailsEvent()
    data class EnteredDescription(val value: String): TodoDetailsEvent()
    data class EnteredDate(val value: String): TodoDetailsEvent()
    data class EnteredDeadline(val value: String): TodoDetailsEvent()
}