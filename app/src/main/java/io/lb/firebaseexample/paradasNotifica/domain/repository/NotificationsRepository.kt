package io.lb.firebaseexample.paradasNotifica.domain.repository

interface NotificationsRepository {
    fun initializeFirebaseMessaging()
    suspend fun sendNotification(title: String, message: String, topic: String)
}