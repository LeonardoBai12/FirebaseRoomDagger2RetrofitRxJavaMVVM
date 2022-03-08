package io.lb.firebaseexample.todo_feature.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.lb.firebaseexample.di.ViewModelKey
import io.lb.firebaseexample.ui.todo.TodoViewModel

@Module
abstract class TodoViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TodoViewModel::class)
    abstract fun bindTodoViewModel(viewModel: TodoViewModel) : ViewModel
}