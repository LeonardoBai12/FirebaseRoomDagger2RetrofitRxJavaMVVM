package io.lb.firebaseexample.user_feature.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.lb.firebaseexample.di.ViewModelKey
import io.lb.firebaseexample.user_feature.presentation.login.LoginViewModel

@Module
abstract class UserViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindUserViewModel(viewModel: LoginViewModel) : ViewModel
}