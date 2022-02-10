package io.lb.firebaseexample.ui.todo

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import io.lb.firebaseexample.model.todo.Todo
import io.lb.firebaseexample.network.todo.TodoRepository
import javax.inject.Inject

class TodoViewModel @Inject constructor(
    private val repository: TodoRepository,
) : ViewModel() {
    fun createTodo(
        id: Int,
        typedTitle: String,
        typedDescription: String,
        typedDate: String,
        typedDeadline: String
    ): Todo {
        return Todo(
            id = id,
            title = typedTitle,
            description = typedDescription,
            date = typedDate,
            deadline = typedDeadline,
            isCompleted = false
        )
    }

    fun validateTodo(todo: Todo): Boolean {
        return true
    }

    fun insertTodo(todo: Todo) {
    }

    fun loadTodos(): Task<DataSnapshot> {
        return repository.loadTodos()
    }

    fun loadTodosListener(onDataChanged: (ArrayList<Todo>) -> Unit): ValueEventListener {
        return repository.loadTodosListener(onDataChanged)
    }
}