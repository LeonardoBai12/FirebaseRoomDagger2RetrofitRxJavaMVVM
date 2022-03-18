package io.lb.firebaseexample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.lb.firebaseexample.todo_feature.di.MainFragmentBuildersModule
import io.lb.firebaseexample.todo_feature.di.TodoModule
import io.lb.firebaseexample.todo_feature.di.MainTodosViewModelModule
import io.lb.firebaseexample.user_feature.di.UserModule
import io.lb.firebaseexample.user_feature.di.UserViewModelModule
import io.lb.firebaseexample.user_feature.presentation.login.LoginActivity
import io.lb.firebaseexample.user_feature.presentation.sign_in.SignInActivity
import io.lb.firebaseexample.todo_feature.presentation.todo.MainActivity
import io.lb.firebaseexample.not_connected_feature.presentation.NotConnectedActivity
import io.lb.firebaseexample.splash_feature.presentation.SplashActivity
import io.lb.firebaseexample.todo_feature.di.TodoDetailViewModelModule
import io.lb.firebaseexample.todo_feature.presentation.todo_details.TodoDetailsActivity

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
            TodoModule::class,
            MainTodosViewModelModule::class,
            MainFragmentBuildersModule::class,
        ]
    )
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [
            TodoModule::class,
            TodoDetailViewModelModule::class,
        ]
    )
    abstract fun contributeTodoActivity(): TodoDetailsActivity
}