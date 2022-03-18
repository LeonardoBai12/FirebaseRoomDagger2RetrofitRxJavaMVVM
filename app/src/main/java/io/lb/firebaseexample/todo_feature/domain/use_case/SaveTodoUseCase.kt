package io.lb.firebaseexample.todo_feature.domain.use_case

import com.google.android.gms.tasks.Task
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.todo_feature.domain.repository.TodoRepository

class SaveTodoUseCase(
    private val repository: TodoRepository
) {
    operator fun invoke(id: Int, todo: Todo): Task<Void> {
        return repository.insertTodo(id, todo)
    }
}