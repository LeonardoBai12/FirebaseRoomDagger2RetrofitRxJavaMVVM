package io.lb.firebaseexample.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.lb.firebaseexample.db.AppDatabase
import io.lb.firebaseexample.notification_feature.data.data_source.FirebaseNotificationService
import io.lb.firebaseexample.notification_feature.data.data_source.NotificationService
import io.lb.firebaseexample.settings_feature.data.data_source.SettingsDataSource
import io.lb.firebaseexample.util.GeneralConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GeneralConstants.FIREBASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesNotificationService(retrofit: Retrofit): NotificationService {
        return retrofit.create(NotificationService::class.java)
    }

    @Provides
    @Singleton
    fun providesFirebaseNotificationService(): FirebaseNotificationService {
        return FirebaseNotificationService()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun getFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun getFirebaseMessaging(): FirebaseMessaging {
        return Firebase.messaging
    }

    @Provides
    @Singleton
    fun getFirebaseDatabase(): FirebaseDatabase {
        return Firebase.database
    }

    @Provides
    @Singleton
    fun providesSettingsDataSource(context: Application): SettingsDataSource {
        return SettingsDataSource(context)
    }
}