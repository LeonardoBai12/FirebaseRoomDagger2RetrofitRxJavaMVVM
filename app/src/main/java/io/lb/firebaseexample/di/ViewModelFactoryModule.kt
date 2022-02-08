package io.lb.firebaseexample.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import io.lb.firebaseexample.core.ViewModelProviderFactory

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(
        providerFactory : ViewModelProviderFactory
    ) : ViewModelProvider.Factory
}