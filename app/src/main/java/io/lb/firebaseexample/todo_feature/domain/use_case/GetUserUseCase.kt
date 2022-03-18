package io.lb.firebaseexample.todo_feature.domain.use_case

import io.lb.firebaseexample.todo_feature.domain.repository.TodoRepository
import io.lb.firebaseexample.user_feature.domain.model.User

class GetUserUseCase(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(): User? {
        return repository.getUser()
    }
}