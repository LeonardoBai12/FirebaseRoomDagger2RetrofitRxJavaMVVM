package io.lb.firebaseexample.notification_feature.presentation

sealed class NotificationEvent {
    data class OnNotificationSent(
        val title: String,
        val message: String,
        val topic: String,
    ): NotificationEvent()
    data class OnInitializeMessaging(
        val topic: String
    ): NotificationEvent()
    data class OnScheduleNotification(
        val title: String,
        val day: Int,
        val month: Int,
        val year: Int,
    ): NotificationEvent()
    data class OnScheduleNotificationDeactivate(
        val day: Int,
        val month: Int,
        val year: Int,
    ): NotificationEvent()
}