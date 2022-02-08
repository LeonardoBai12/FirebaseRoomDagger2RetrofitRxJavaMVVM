package io.lb.firebaseexample.di.todo

import dagger.Module
import dagger.Provides
import io.lb.firebaseexample.network.RetrofitServiceInterface
import io.lb.firebaseexample.network.todo.TodoRepository

@Module
class TodoModule {
    @Provides
    fun providesTodosRepository(
        retrofitServiceInterface: RetrofitServiceInterface,
    ): TodoRepository {
        return TodoRepository(retrofitServiceInterface)
    }
}