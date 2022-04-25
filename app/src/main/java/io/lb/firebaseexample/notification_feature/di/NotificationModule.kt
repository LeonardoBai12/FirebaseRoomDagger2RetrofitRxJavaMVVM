package io.lb.firebaseexample.notification_feature.di

import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.lb.firebaseexample.notification_feature.data.data_source.NotificationDataSource
import io.lb.firebaseexample.notification_feature.data.data_source.NotificationService
import io.lb.firebaseexample.notification_feature.data.data_source.ScheduledNotificationDataSource
import io.lb.firebaseexample.notification_feature.data.repository.NotificationsRepositoryImpl
import io.lb.firebaseexample.notification_feature.domain.repository.NotificationsRepository
import io.lb.firebaseexample.notification_feature.domain.use_case.*

@Module
@InstallIn(ViewModelComponent::class)
object NotificationModule {
    @Provides
    fun providesNotificationRepository(
        scheduledNotificationDataSource: ScheduledNotificationDataSource,
        notificationService: NotificationService,
        firebaseMessaging: FirebaseMessaging,
        dataSource: NotificationDataSource,
    ): NotificationsRepository {
        return NotificationsRepositoryImpl(
            scheduledNotificationDataSource,
            notificationService,
            firebaseMessaging,
            dataSource,
        )
    }

    @Provides
    fun providesNotificationUseCases(repository: NotificationsRepository): NotificationUseCases {
        return NotificationUseCases(
            sendPushNotificationToUseCase = SendPushNotificationToUseCase(repository),
            initializeFireBaseMessagingUseCase = InitializeFireBaseMessagingUseCase(repository),
            sendScheduledNotificationUseCase = SendScheduledNotificationUseCase(repository),
            deactivateScheduledNotificationUseCase = DeactivateScheduledNotificationUseCase(repository),
        )
    }
}