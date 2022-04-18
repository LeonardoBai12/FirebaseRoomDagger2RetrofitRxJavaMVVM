package io.lb.firebaseexample.notification_feature.di

import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.lb.firebaseexample.notification_feature.data.data_source.FirebaseNotificationService
import io.lb.firebaseexample.notification_feature.data.data_source.NotificationService
import io.lb.firebaseexample.notification_feature.data.repository.NotificationsRepositoryImpl
import io.lb.firebaseexample.notification_feature.domain.repository.NotificationsRepository
import io.lb.firebaseexample.notification_feature.domain.use_case.NotificationUseCases
import io.lb.firebaseexample.notification_feature.domain.use_case.SendPushNotificationUseCase

@Module
@InstallIn(ViewModelComponent::class)
object NotificationModule {
    @Provides
    fun providesNotificationRepository(
        notificationService: NotificationService,
        firebaseMessaging: FirebaseMessaging,
        firebaseService: FirebaseNotificationService,
    ): NotificationsRepository {
        return NotificationsRepositoryImpl(
            notificationService,
            firebaseMessaging,
            firebaseService,
        )
    }

    @Provides
    fun providesNotificationUseCases(repository: NotificationsRepository): NotificationUseCases {
        return NotificationUseCases(
            sendPushNotificationUseCase = SendPushNotificationUseCase(repository)
        )
    }
}