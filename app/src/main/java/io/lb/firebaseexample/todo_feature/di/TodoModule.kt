package io.lb.firebaseexample.todo_feature.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import io.lb.firebaseexample.db.AppDatabase
import io.lb.firebaseexample.todo_feature.data.data_source.TodoDAO
import io.lb.firebaseexample.todo_feature.data.data_source.TodoDataSource

@Module
class TodoModule {
    @Provides
    fun providesTodosRepository(
        database: FirebaseDatabase,
        auth: FirebaseAuth
    ): TodoDataSource {
        return TodoDataSource(database, auth)
    }

    @Provides
    fun getAppDao(appDataBase: AppDatabase): TodoDAO {
        return appDataBase.todoDao
    }
}