package io.lb.firebaseexample.di.user

import dagger.Module
import dagger.Provides
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
}