package io.lb.firebaseexample.di.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
        database: FirebaseDatabase,
        auth: FirebaseAuth
    ): UserRepository {
        return UserRepository(database, auth)
    }

    @Provides
    fun getAppDao(appDataBase: AppDataBase): UserDAO {
        return appDataBase.getUserDao()
    }
}