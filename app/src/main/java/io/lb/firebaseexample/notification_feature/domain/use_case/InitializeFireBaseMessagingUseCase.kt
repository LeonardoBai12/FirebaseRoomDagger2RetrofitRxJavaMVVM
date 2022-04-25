package io.lb.firebaseexample.notification_feature.domain.use_case

import io.lb.firebaseexample.notification_feature.domain.repository.NotificationsRepository

class InitializeFireBaseMessagingUseCase(
    private val repository: NotificationsRepository
) {
    suspend operator fun invoke(topic: String) {
        repository.initializeFirebaseMessaging(topic)
    }
}