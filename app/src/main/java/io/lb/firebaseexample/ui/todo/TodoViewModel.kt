package io.lb.firebaseexample.ui.todo

import androidx.lifecycle.ViewModel
import io.lb.firebaseexample.network.todo.TodoRepository
import javax.inject.Inject

class TodoViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {
    //fun makeQuery(): LiveData<ArrayList<Todo>> {
    //    // Um exemplo caso quiséssemos transformar um observável em LiveData
    //    return repository.makeReactiveQueryForTodos().toLiveData()
    //}
}