package io.lb.firebaseexample.todo_feature.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.lb.firebaseexample.di.ViewModelKey
import io.lb.firebaseexample.todo_feature.presentation.todo_details.TodoDetailsViewModel

@Module
abstract class TodoDetailViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TodoDetailsViewModel::class)
    abstract fun bindTodoDetailsViewModel(viewModel: TodoDetailsViewModel) : ViewModel
}