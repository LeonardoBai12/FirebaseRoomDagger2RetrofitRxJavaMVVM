package io.lb.firebaseexample.paradasNotifica.domain.model

import io.lb.firebaseexample.paradasNotifica.domain.model.NotificationData

data class PushNotification(
    val data: NotificationData,
    val to: String? = null,
)
