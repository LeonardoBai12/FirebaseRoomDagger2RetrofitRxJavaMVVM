package io.lb.firebaseexample.notification_feature.domain.repository

interface NotificationsRepository {
    fun initializeFirebaseMessaging(topic: String)
    suspend fun sendNotification(title: String, message: String)
}