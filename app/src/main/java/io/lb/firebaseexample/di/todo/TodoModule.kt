package io.lb.firebaseexample.di.todo

import dagger.Module
import dagger.Provides
import io.lb.firebaseexample.db.AppDataBase
import io.lb.firebaseexample.db.todo.TodoDAO
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

    @Provides
    fun getAppDao(appDataBase: AppDataBase): TodoDAO {
        return appDataBase.getTodoDao()
    }
}