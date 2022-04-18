package io.lb.firebaseexample.notification_feature.domain.use_case

import io.lb.firebaseexample.notification_feature.domain.repository.NotificationsRepository

class SendPushNotificationToUseCase(
    private val repository: NotificationsRepository
) {
    suspend operator fun invoke(title: String, message: String, topic: String) {
        repository.sendNotificationTo(title, message, topic)
    }
}