package io.lb.firebaseexample.di.todo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
        database: FirebaseDatabase,
        auth: FirebaseAuth
    ): TodoRepository {
        return TodoRepository(database, auth)
    }

    @Provides
    fun getAppDao(appDataBase: AppDataBase): TodoDAO {
        return appDataBase.getTodoDao()
    }
}