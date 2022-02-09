package io.lb.firebaseexample.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.lb.firebaseexample.di.main.MainFragmentBuildersModule
import io.lb.firebaseexample.di.todo.TodoModule
import io.lb.firebaseexample.di.todo.TodoViewModelModule
import io.lb.firebaseexample.di.user.UserModule
import io.lb.firebaseexample.di.user.UserViewModelModule
import io.lb.firebaseexample.ui.login.LoginActivity
import io.lb.firebaseexample.ui.login.SignInActivity
import io.lb.firebaseexample.ui.main.MainActivity
import io.lb.firebaseexample.ui.splash.SplashActivity
import io.lb.firebaseexample.ui.todo.TodoDetailsActivity

@Module
abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

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