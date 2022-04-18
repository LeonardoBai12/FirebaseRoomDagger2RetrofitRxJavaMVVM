package io.lb.firebaseexample.notification_feature.domain.use_case

import io.lb.firebaseexample.notification_feature.domain.repository.NotificationsRepository

class SendPushNotificationUseCase(
    private val repository: NotificationsRepository
) {
    suspend operator fun invoke(title: String, message: String, topic: String) {
        repository.initializeFirebaseMessaging(topic)
        repository.sendNotification(title, message)
    }
}