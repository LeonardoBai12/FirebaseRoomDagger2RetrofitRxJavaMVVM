package io.lb.firebaseexample.notification_feature.domain.use_case

data class NotificationUseCases(
    val sendPushNotificationToUseCase: SendPushNotificationToUseCase,
    val initializeFireBaseMessagingUseCase: InitializeFireBaseMessagingUseCase,
    val sendScheduledNotificationUseCase: SendScheduledNotificationUseCase,
    val deactivateScheduledNotificationUseCase: DeactivateScheduledNotificationUseCase,
)