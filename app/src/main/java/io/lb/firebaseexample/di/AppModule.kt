package io.lb.firebaseexample.di

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import dagger.Module
import dagger.Provides
import io.lb.firebaseexample.db.AppDataBase
import io.lb.firebaseexample.network.RetrofitServiceInterface
import io.lb.firebaseexample.util.GeneralConstants
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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
    fun getAppDataBase(application: Application): AppDataBase {
        return AppDataBase.getAppDataBaseInstance(application)
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