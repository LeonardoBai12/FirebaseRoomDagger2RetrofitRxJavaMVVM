package io.lb.firebaseexample.di

import android.app.Application
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
    @Provides
    @Singleton
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GeneralConstants.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun getRetrofitServiceInstance(retrofit: Retrofit): RetrofitServiceInterface {
        return retrofit.create(RetrofitServiceInterface::class.java)
    }

    @Provides
    @Singleton
    fun getAppDataBase(application: Application): AppDataBase {
        return AppDataBase.getAppDataBaseInstance(application)
    }
}