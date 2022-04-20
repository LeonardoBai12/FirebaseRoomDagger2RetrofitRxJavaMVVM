package io.lb.firebaseexample.notification_feature.data.repository

import android.content.Context
import android.os.Build
import com.google.firebase.messaging.FirebaseMessaging
import io.lb.firebaseexample.notification_feature.data.data_source.NotificationDataSource
import io.lb.firebaseexample.notification_feature.data.data_source.NotificationService
import io.lb.firebaseexample.notification_feature.data.data_source.ScheduledNotificationDataSource
import io.lb.firebaseexample.notification_feature.domain.model.NotificationData
import io.lb.firebaseexample.notification_feature.domain.model.PushNotification
import io.lb.firebaseexample.notification_feature.domain.repository.NotificationsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class NotificationsRepositoryImpl(
    private val scheduledNotificationDataSource: ScheduledNotificationDataSource,
    private val notificationService: NotificationService,
    private val firebaseMessaging: FirebaseMessaging,
    private val dataSource: NotificationDataSource,
): NotificationsRepository{
    override fun initializeFirebaseMessaging(topic: String) {
        firebaseMessaging.subscribeToTopic(topic)
        firebaseMessaging.token.addOnCompleteListener {
            dataSource.token = it.result
        }
    }

    override suspend fun sendNotificationTo(title: String, message: String, to: String?) {
        PushNotification(
            NotificationData(title, message),
            to
        ).also {
            sendNotification(it)
        }
    }

    override fun sendScheduledNotification(
        context: Context,
        title: String,
        message: String,
        day: Int,
        month: Int,
        year: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            scheduledNotificationDataSource.scheduleNotification(
                title, message, day, month, year
            )
        }
    }

    private suspend fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = notificationService.postNotification(notification)
            if(!response.isSuccessful) {
                Timber.e(response.errorBody().toString())
            }
        } catch(e: Exception) {
            Timber.e(e.toString())
        }
    }
}