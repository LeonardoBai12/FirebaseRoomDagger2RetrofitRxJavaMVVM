package io.lb.firebaseexample.todo_feature.data.repository

import com.google.android.gms.tasks.Task
import io.lb.firebaseexample.todo_feature.data.data_source.TodoDAO
import io.lb.firebaseexample.todo_feature.data.data_source.TodoDataSource
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.todo_feature.domain.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TodoRepositoryImpl(
    private val dataSource: TodoDataSource,
    private val dao: TodoDAO
): TodoRepository {
    override fun insertTodo(id: Int, headset: Todo): Task<Void> {
        return dataSource.insertTodo(id, headset)
    }

    override suspend fun getTodos(): List<Todo> {
        updateDatabase()
        return dao.getAllRecords()
    }

    private suspend fun updateDatabase() = withContext(Dispatchers.IO) {
        val todos = runCatching {
            dataSource.getTodos()
        }.getOrNull() ?: return@withContext

        dao.deleteAllRecords()
        todos.forEach {
            dao.insertRecord(it)
        }
    }

    override fun logout() {
        dataSource.logout()
    }
}