package io.lb.firebaseexample.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.lb.firebaseexample.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class FirebaseExampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}