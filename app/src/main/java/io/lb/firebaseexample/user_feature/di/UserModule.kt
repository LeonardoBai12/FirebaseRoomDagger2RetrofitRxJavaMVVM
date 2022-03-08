package io.lb.firebaseexample.user_feature.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import io.lb.firebaseexample.db.AppDatabase
import io.lb.firebaseexample.user_feature.data.data_source.UserDAO
import io.lb.firebaseexample.user_feature.data.data_source.UserDataSource
import io.lb.firebaseexample.user_feature.data.repository.UserRepositoryImpl
import io.lb.firebaseexample.user_feature.domain.repository.UserRepository
import io.lb.firebaseexample.user_feature.domain.use_case.CreateFirebaseUserUseCase
import io.lb.firebaseexample.user_feature.domain.use_case.LoginFirebaseUserUseCase
import io.lb.firebaseexample.user_feature.domain.use_case.InsertUserUseCase
import io.lb.firebaseexample.user_feature.domain.use_case.UserUseCases

@Module
class UserModule {
    @Provides
    fun providesTodosDataSource(
        database: FirebaseDatabase,
        auth: FirebaseAuth
    ): UserDataSource {
        return UserDataSource(database, auth)
    }

    @Provides
    fun getAppDao(appDataBase: AppDatabase): UserDAO {
        return appDataBase.userDao
    }

    @Provides
    fun providesUserRepository(dataSource: UserDataSource, dao: UserDAO): UserRepository {
        return UserRepositoryImpl(dataSource, dao)
    }

    @Provides
    fun providesUserUseCases(repository: UserRepository): UserUseCases {
        return UserUseCases(
            loginUserUseCase = LoginFirebaseUserUseCase(repository),
            createUserUseCase = CreateFirebaseUserUseCase(repository),
            insertUserUseCase = InsertUserUseCase(repository)
        )
    }
}