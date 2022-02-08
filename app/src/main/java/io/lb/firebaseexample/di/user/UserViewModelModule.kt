package io.lb.firebaseexample.di.user

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.lb.firebaseexample.di.ViewModelKey
import io.lb.firebaseexample.ui.todo.TodoViewModel
import io.lb.firebaseexample.ui.user.UserViewModel

@Module
abstract class UserViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TodoViewModel::class)
    abstract fun bindUserViewModel(viewModel: UserViewModel) : ViewModel
}