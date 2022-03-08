package io.lb.firebaseexample.user_feature.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import io.lb.firebaseexample.db.AppDatabase
import io.lb.firebaseexample.user_feature.data.data_source.UserDAO
import io.lb.firebaseexample.user_feature.data.data_source.UserDataSource

@Module
class UserModule {
    @Provides
    fun providesTodosRepository(
        database: FirebaseDatabase,
        auth: FirebaseAuth
    ): UserDataSource {
        return UserDataSource(database, auth)
    }

    @Provides
    fun getAppDao(appDataBase: AppDatabase): UserDAO {
        return appDataBase.userDao
    }
}