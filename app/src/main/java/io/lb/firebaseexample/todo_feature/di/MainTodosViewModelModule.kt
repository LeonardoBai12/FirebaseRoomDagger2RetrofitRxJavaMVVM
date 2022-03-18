package io.lb.firebaseexample.todo_feature.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.lb.firebaseexample.di.ViewModelKey
import io.lb.firebaseexample.todo_feature.presentation.todo.MainTodosViewModel

@Module
abstract class MainTodosViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainTodosViewModel::class)
    abstract fun bindMainTodosViewModel(viewModel: MainTodosViewModel) : ViewModel
}