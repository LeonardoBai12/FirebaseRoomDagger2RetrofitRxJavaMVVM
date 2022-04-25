package io.lb.firebaseexample.notification_feature.domain.repository

import android.content.Context

interface NotificationsRepository {
    fun initializeFirebaseMessaging(topic: String)
    suspend fun sendNotificationTo(title: String, message: String, to: String?)
    fun sendScheduledNotification(
        context: Context,
        title: String,
        day: Int,
        month: Int,
        year: Int,
    )
}