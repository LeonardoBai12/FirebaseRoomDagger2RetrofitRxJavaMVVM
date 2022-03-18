package io.lb.firebaseexample.todo_feature.domain.use_case

import io.lb.firebaseexample.todo_feature.domain.repository.TodoRepository

class LogoutUseCase(
    private val repository: TodoRepository
) {
    operator fun invoke() {
        repository.logout()
    }
}