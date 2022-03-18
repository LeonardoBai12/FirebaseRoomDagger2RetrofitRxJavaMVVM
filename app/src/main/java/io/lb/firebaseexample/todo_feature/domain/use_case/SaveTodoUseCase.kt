package io.lb.firebaseexample.todo_feature.domain.use_case

import com.google.android.gms.tasks.Task
import io.lb.firebaseexample.todo_feature.domain.model.InvalidTodoException
import io.lb.firebaseexample.todo_feature.domain.repository.TodoRepository
import io.lb.firebaseexample.user_feature.domain.model.InvalidUserException

class SaveTodoUseCase(
    private val repository: TodoRepository
) {
    @Throws(InvalidTodoException::class)
    operator fun invoke(
        id: Int,
        title: String?,
        description: String?,
        date: String?,
        deadline: String?
    ): Task<Void> {
        if (title.isNullOrBlank()) {
            throw InvalidTodoException("Por favor, digite um título")
        }

        return repository.insertTodo(id, title, description, date, deadline)
    }
}