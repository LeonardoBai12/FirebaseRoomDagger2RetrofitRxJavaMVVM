package io.lb.firebaseexample.todo_feature.domain.use_case

import com.google.android.gms.tasks.Task
import io.lb.firebaseexample.todo_feature.domain.model.InvalidTodoException
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.todo_feature.domain.repository.TodoRepository

class UpdateTodo(
    private val repository: TodoRepository
) {
    @Throws(InvalidTodoException::class)
    operator fun invoke(
        id: Int,
        todo: Todo,
    ): Task<Void> {
        return repository.updateTodo(id, todo)
    }
}