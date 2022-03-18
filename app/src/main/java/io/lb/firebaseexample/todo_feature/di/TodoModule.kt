package io.lb.firebaseexample.todo_feature.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import io.lb.firebaseexample.db.AppDatabase
import io.lb.firebaseexample.todo_feature.data.data_source.TodoDAO
import io.lb.firebaseexample.todo_feature.data.data_source.TodoDataSource
import io.lb.firebaseexample.todo_feature.data.repository.TodoRepositoryImpl
import io.lb.firebaseexample.todo_feature.domain.repository.TodoRepository
import io.lb.firebaseexample.todo_feature.domain.use_case.GetTodosUseCase
import io.lb.firebaseexample.todo_feature.domain.use_case.LogoutUseCase
import io.lb.firebaseexample.todo_feature.domain.use_case.SaveTodoUseCase
import io.lb.firebaseexample.todo_feature.domain.use_case.TodoUseCases

@Module
class TodoModule {
    @Provides
    fun providesTodoDataSource(
        database: FirebaseDatabase,
        auth: FirebaseAuth
    ): TodoDataSource {
        return TodoDataSource(database, auth)
    }

    @Provides
    fun getAppDao(appDataBase: AppDatabase): TodoDAO {
        return appDataBase.todoDao
    }

    @Provides
    fun providesTodoRepository(dataSource: TodoDataSource, dao: TodoDAO): TodoRepository {
        return TodoRepositoryImpl(dataSource, dao)
    }

    @Provides
    fun providesTodoUseCases(repository: TodoRepository): TodoUseCases {
        return TodoUseCases(
            getTodosUseCase = GetTodosUseCase(repository),
            logoutUseCase = LogoutUseCase(repository),
            saveTodoUseCase = SaveTodoUseCase(repository)
        )
    }
}