package io.lb.firebaseexample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.lb.firebaseexample.todo_feature.di.MainFragmentBuildersModule
import io.lb.firebaseexample.todo_feature.di.TodoModule
import io.lb.firebaseexample.todo_feature.di.TodoViewModelModule
import io.lb.firebaseexample.user_feature.di.UserModule
import io.lb.firebaseexample.user_feature.di.UserViewModelModule
import io.lb.firebaseexample.ui.login.LoginActivity
import io.lb.firebaseexample.ui.login.SignInActivity
import io.lb.firebaseexample.ui.main.MainActivity
import io.lb.firebaseexample.ui.notConnected.NotConnectedActivity
import io.lb.firebaseexample.ui.splash.SplashActivity
import io.lb.firebaseexample.ui.todo.TodoDetailsActivity

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun contributeNotConnected(): NotConnectedActivity

    @ContributesAndroidInjector(
        modules = [
            UserModule::class,
            UserViewModelModule::class,
        ]
    )
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector(
        modules = [
            UserModule::class,
            UserViewModelModule::class,
        ]
    )
    abstract fun contributeSignInActivity(): SignInActivity

    @ContributesAndroidInjector(
        modules = [
            MainFragmentBuildersModule::class,
            TodoModule::class,
            TodoViewModelModule::class,
            UserModule::class,
            UserViewModelModule::class,
        ]
    )
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [
            TodoModule::class,
            TodoViewModelModule::class,
        ]
    )
    abstract fun contributeTodoActivity(): TodoDetailsActivity
}