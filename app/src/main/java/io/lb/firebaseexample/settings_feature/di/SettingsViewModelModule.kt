package io.lb.firebaseexample.settings_feature.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.lb.firebaseexample.di.ViewModelKey
import io.lb.firebaseexample.settings_feature.presentation.SettingsViewModel

@Module
abstract class SettingsViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewModel: SettingsViewModel) : ViewModel
}