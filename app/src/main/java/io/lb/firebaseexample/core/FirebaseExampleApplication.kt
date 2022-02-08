package io.lb.firebaseexample.core

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.lb.firebaseexample.BuildConfig
import io.lb.firebaseexample.di.DaggerAppComponent
import timber.log.Timber

class FirebaseExampleApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        return DaggerAppComponent.builder().application(this).build()
    }
}