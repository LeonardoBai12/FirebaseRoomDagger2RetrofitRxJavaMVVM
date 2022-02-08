package io.lb.firebaseexample.di.main

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.lb.firebaseexample.ui.main.MainSettingsFragment
import io.lb.firebaseexample.ui.main.MainTodosFragment

@Module
abstract class MainFragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainTodosFragment() : MainTodosFragment

    @ContributesAndroidInjector
    abstract fun contributeMainSettingsFragment() : MainSettingsFragment
}