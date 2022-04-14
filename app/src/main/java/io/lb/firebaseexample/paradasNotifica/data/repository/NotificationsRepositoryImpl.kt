package io.lb.firebaseexample.paradasNotifica.data.repository

import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import io.lb.firebaseexample.paradasNotifica.data.dataSource.FirebaseService
import io.lb.firebaseexample.paradasNotifica.data.dataSource.NotificationService
import io.lb.firebaseexample.paradasNotifica.domain.model.NotificationData
import io.lb.firebaseexample.paradasNotifica.domain.model.PushNotification
import io.lb.firebaseexample.paradasNotifica.domain.repository.NotificationsRepository
import io.lb.firebaseexample.util.GeneralConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class NotificationsRepositoryImpl(
    private val notificationService: NotificationService,
    private val firebaseMessaging: FirebaseMessaging,
    private val firebaseService: FirebaseService,
): NotificationsRepository{
    override fun initializeFirebaseMessaging() {
        firebaseMessaging.subscribeToTopic(GeneralConstants.TOPIC_DEADLINE)
        firebaseMessaging.token.addOnCompleteListener {
            firebaseService.token = it.result
        }
    }

    override suspend fun sendNotification(title: String, message: String, topic: String) {
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