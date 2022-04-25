package io.lb.firebaseexample.notification_feature.data.data_source

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import io.lb.firebaseexample.util.buildNotification
import io.lb.firebaseexample.util.defaultNotification
import kotlin.random.Random


class NotificationBroadcast : BroadcastReceiver() {
    companion object {
        const val CHANNEL_ID = "scheduled_notification_channel"
    }
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("titleExtra") ?: ""
        val message = intent.getStringExtra("messageExtra") ?: ""
        val id = intent.getIntExtra("notificationId", 0)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = notificationManager.buildNotification(
            context,
            CHANNEL_ID,
            title,
            message
        )

        notificationManager.notify(id, notification)
    }
}