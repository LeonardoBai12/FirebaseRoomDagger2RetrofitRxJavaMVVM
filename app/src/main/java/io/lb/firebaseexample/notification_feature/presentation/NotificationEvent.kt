package io.lb.firebaseexample.notification_feature.presentation

sealed class NotificationEvent {
    data class OnNotificationSent(
        val title: String,
        val message: String,
        val topic: String,
    ): NotificationEvent()
}