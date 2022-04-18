package io.lb.firebaseexample.notification_feature.data.repository

import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import io.lb.firebaseexample.notification_feature.data.data_source.FirebaseNotificationService
import io.lb.firebaseexample.notification_feature.data.data_source.NotificationService
import io.lb.firebaseexample.notification_feature.domain.model.NotificationData
import io.lb.firebaseexample.notification_feature.domain.model.PushNotification
import io.lb.firebaseexample.notification_feature.domain.repository.NotificationsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class NotificationsRepositoryImpl(
    private val notificationService: NotificationService,
    private val firebaseMessaging: FirebaseMessaging,
    private val firebaseService: FirebaseNotificationService,
): NotificationsRepository{
    override fun initializeFirebaseMessaging(topic: String) {
        firebaseMessaging.subscribeToTopic(topic)
        firebaseMessaging.token.addOnCompleteListener {
            firebaseService.token = it.result
        }
    }

    override suspend fun sendNotification(title: String, message: String) {
        PushNotification(
            NotificationData(title, message)
        ).also {
            sendNotification(it)
        }
    }

    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = notificationService.postNotification(notification)
            if(response.isSuccessful) {
                Timber.d("Response: ${Gson().toJson(response)}")
            } else {
                Timber.e(response.errorBody().toString())
            }
        } catch(e: Exception) {
            Timber.e(e.toString())
        }
    }
}