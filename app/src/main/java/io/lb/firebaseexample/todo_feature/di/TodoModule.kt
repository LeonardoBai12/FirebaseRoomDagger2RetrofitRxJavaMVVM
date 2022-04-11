package io.lb.firebaseexample.todo_feature.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.lb.firebaseexample.db.AppDatabase
import io.lb.firebaseexample.todo_feature.data.data_source.TodoDAO
import io.lb.firebaseexample.todo_feature.data.data_source.TodoDataSource
import io.lb.firebaseexample.todo_feature.data.repository.TodoRepositoryImpl
import io.lb.firebaseexample.todo_feature.domain.repository.TodoRepository
import io.lb.firebaseexample.todo_feature.domain.use_case.*
import io.lb.firebaseexample.user_feature.data.data_source.UserDAO

@Module
@InstallIn(ViewModelComponent::class)
object TodoModule {
    @Provides
    fun providesTodoDataSource(
        database: FirebaseDatabase,
        auth: FirebaseAuth
    ): TodoDataSource {
        return TodoDataSource(database, auth)
    }

    @Provides
    fun getTodoDao(appDataBase: AppDatabase): TodoDAO {
        return appDataBase.todoDao
    }

    @Provides
    fun providesTodoRepository(
        dataSource: TodoDataSource,
        todoDao: TodoDAO,
        userDao: UserDAO
    ): TodoRepository {
        return TodoRepositoryImpl(dataSource, todoDao, userDao)
    }

    @Provides
    fun providesTodoUseCases(repository: TodoRepository): TodoUseCases {
        return TodoUseCases(
            getTodosUseCase = GetTodosUseCase(repository),
            updateTodo = UpdateTodo(repository),
            logoutUseCase = LogoutUseCase(repository),
            saveTodoUseCase = SaveTodoUseCase(repository),
            getUserUseCase = GetUserUseCase(repository),
        )
    }
}