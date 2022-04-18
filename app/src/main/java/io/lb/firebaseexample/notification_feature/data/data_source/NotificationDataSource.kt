package io.lb.firebaseexample.notification_feature.data.data_source

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.lb.firebaseexample.util.buildNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

private const val CHANNEL_ID = "firebase_example_channel"

class NotificationDataSource: FirebaseMessagingService() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "notification_token")
    private val dataStoreKey = stringPreferencesKey("token")
    var token: String? = null

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)

        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit {
                it[dataStoreKey] = newToken
                token = newToken
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = notificationManager.buildNotification(
            this,
            CHANNEL_ID,
            message.data["title"] ?: "",
            message.data["message"] ?: ""
        )
        notificationManager.notify(Random.nextInt(), notification)
    }
}