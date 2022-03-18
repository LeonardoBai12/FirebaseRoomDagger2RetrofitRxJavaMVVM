package io.lb.firebaseexample.todo_feature.domain.repository

import com.google.android.gms.tasks.Task
import io.lb.firebaseexample.todo_feature.domain.model.Todo

interface TodoRepository {
    fun insertTodo(
        id: Int,
        title: String,
        description: String?,
        date: String?,
        deadline: String?
    ): Task<Void>
    suspend fun getTodos(): List<Todo>
    fun logout()
}