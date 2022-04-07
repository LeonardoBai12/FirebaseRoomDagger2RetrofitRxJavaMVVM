package io.lb.firebaseexample.todo_feature.domain.repository

import com.google.android.gms.tasks.Task
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.user_feature.domain.model.User
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun insertTodo(
        id: Int,
        title: String,
        description: String?,
        date: String?,
        deadline: String?
    ): Task<Void>
    suspend fun getTodos(): Flow<List<Todo>>
    fun logout()
    suspend fun getUser(): User?
}