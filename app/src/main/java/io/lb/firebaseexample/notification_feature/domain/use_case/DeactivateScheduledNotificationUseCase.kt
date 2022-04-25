package io.lb.firebaseexample.notification_feature.domain.use_case

import io.lb.firebaseexample.notification_feature.domain.repository.NotificationsRepository

class DeactivateScheduledNotificationUseCase(
    private val repository: NotificationsRepository
) {
    operator fun invoke(day: Int, month: Int, year: Int) {
        repository.deactivateScheduledNotification(day, month, year)
    }
}