package io.lb.firebaseexample.di.user

import dagger.Module
import dagger.Provides
import io.lb.firebaseexample.db.AppDataBase
import io.lb.firebaseexample.db.user.UserDAO
import io.lb.firebaseexample.network.RetrofitServiceInterface
import io.lb.firebaseexample.network.user.UserRepository

@Module
class UserModule {
    @Provides
    fun providesTodosRepository(
        retrofitServiceInterface: RetrofitServiceInterface,
    ): UserRepository {
        return UserRepository(retrofitServiceInterface)
    }

    @Provides
    fun getAppDao(appDataBase: AppDataBase): UserDAO {
        return appDataBase.getUserDao()
    }
}