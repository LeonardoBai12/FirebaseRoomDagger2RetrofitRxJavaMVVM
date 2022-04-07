package io.lb.firebaseexample.todo_feature.domain.use_case

import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.todo_feature.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class GetTodosUseCase(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(): Flow<List<Todo>?> {
        return repository.getTodos()
    }
}