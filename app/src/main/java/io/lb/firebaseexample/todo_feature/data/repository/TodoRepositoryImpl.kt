package io.lb.firebaseexample.todo_feature.data.repository

import com.google.android.gms.tasks.Task
import io.lb.firebaseexample.todo_feature.data.data_source.TodoDAO
import io.lb.firebaseexample.todo_feature.data.data_source.TodoDataSource
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.todo_feature.domain.repository.TodoRepository
import io.lb.firebaseexample.user_feature.data.data_source.UserDAO
import io.lb.firebaseexample.user_feature.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class TodoRepositoryImpl(
    private val dataSource: TodoDataSource,
    private val todoDAO: TodoDAO,
    private val userDAO: UserDAO
): TodoRepository {
    override fun insertTodo(
        id: Int,
        title: String,
        description: String?,
        date: String?,
        deadline: String?
    ): Task<Void> {
        val todo = Todo(
            title = title,
            description = description,
            date = date,
            deadline = deadline,
            isCompleted = false,
        )
        return dataSource.insertTodo(id, todo)
    }

    override suspend fun getTodos(): Flow<List<Todo>> {
        updateDatabase()
        return flow {
            emit(todoDAO.getAllRecords())
        }
    }

    private suspend fun updateDatabase() = withContext(Dispatchers.IO) {
        val todos = runCatching {
            dataSource.getTodos()
        }.getOrNull() ?: return@withContext

        todoDAO.deleteAllRecords()

        todos.forEach {
            todoDAO.insertRecord(it)
        }
    }

    override fun logout() {
        dataSource.logout()
    }

    override suspend fun getUser(): User {
        return userDAO.getUsers()[0]
    }
}