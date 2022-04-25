package io.lb.firebaseexample.notification_feature.domain.use_case

import io.lb.firebaseexample.notification_feature.domain.repository.NotificationsRepository

class SendScheduledNotificationUseCase(
    private val repository: NotificationsRepository
) {
    operator fun invoke(
        title: String,
        day: Int,
        month: Int,
        year: Int,
    ) {
        repository.sendScheduledNotification(title, day, month, year)
    }
}