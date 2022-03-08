package io.lb.firebaseexample.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import io.lb.firebaseexample.db.AppDatabase
import javax.inject.Singleton

@Module
class AppModule {
//    @Provides
//    @Singleton
//    fun getRetrofitInstance(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(GeneralConstants.BASE_URL)
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun getRetrofitServiceInstance(retrofit: Retrofit): RetrofitServiceInterface {
//        return retrofit.create(RetrofitServiceInterface::class.java)
//    }

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
    fun getFirebaseDatabase(): FirebaseDatabase {
        return Firebase.database
    }
}