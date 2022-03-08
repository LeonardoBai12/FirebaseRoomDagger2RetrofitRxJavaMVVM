package io.lb.firebaseexample.todo_feature.presentation

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.todo_feature.data.data_source.TodoDataSource
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TodoViewModel @Inject constructor(
    private val repository: TodoDataSource,
) : ViewModel() {
    val error = MutableLiveData<String>()

    fun createTodo(
        id: Int,
        typedTitle: String,
        typedDescription: String,
        typedDate: String,
        typedDeadline: String,
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

    fun validateTodo(todo: Todo, hasDeadline: Boolean): Boolean {
        if (todo.title.isNullOrEmpty()) {
            error.value = "Não é possível gravar uma tarefa sem título"
            return false
        } else if (todo.date.isNullOrEmpty()) {
            error.value = "Não é possível gravar uma tarefa sem data"
            return false
        } else if (hasDeadline && todo.deadline.isNullOrEmpty()) {
            error.value = "Esta tarefa necessita um prazo estipulado"
            return false
        }
        return true
    }

    fun insertTodo(todo: Todo, onCompleted: (Boolean, Exception?) -> Unit): Task<Void> {
        return repository.insertTodo(todo, onCompleted)
    }

    fun loadTodos(): Task<DataSnapshot> {
        return repository.loadTodos()
    }

    fun loadTodosListener(onDataChanged: (ArrayList<Todo>) -> Unit): ValueEventListener {
        return repository.loadTodosListener(onDataChanged)
    }

    fun setupSearchTil(searchView: SearchView): Observable<String> {
        return Observable.create<String> { emitter ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(text: String?): Boolean {
                    if (!emitter.isDisposed) {
                        emitter.onNext(text ?: "")
                    }
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
            })
        }.debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}