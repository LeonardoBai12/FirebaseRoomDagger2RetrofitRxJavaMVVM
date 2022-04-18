package io.lb.firebaseexample.notification_feature.domain.use_case

import android.content.Context
import io.lb.firebaseexample.notification_feature.domain.repository.NotificationsRepository

class SendScheduledNotificationUseCase(
    private val repository: NotificationsRepository
) {
    operator fun invoke(
        context: Context,
        title: String,
        message: String,
        day: Int,
        month: Int,
        year: Int,
    ) {
        repository.sendScheduledNotification(context, title, message, day, month, year)
    }
}