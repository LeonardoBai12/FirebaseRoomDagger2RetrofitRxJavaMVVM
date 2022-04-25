package io.lb.firebaseexample.notification_feature.domain.model

data class PushNotification(
    val data: NotificationData,
    val to: String? = null,
)
