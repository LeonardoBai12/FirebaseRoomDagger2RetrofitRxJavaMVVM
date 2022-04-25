package io.lb.firebaseexample.util

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import io.lb.firebaseexample.R
import io.lb.firebaseexample.splash_feature.presentation.SplashActivity
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.S)
fun NotificationManager.buildNotification(
    context: Context,
    channelId: String,
    title: String,
    message: String,
): Notification {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        createNotificationChannel(this, channelId)
    }

    return defaultNotification(context, channelId, title, message)
}

@RequiresApi(Build.VERSION_CODES.S)
fun defaultNotification(
    context: Context,
    channelId: String,
    title: String,
    message: String,
): Notification {
    val intent = Intent(context, SplashActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

    val pendingIntent = TaskStackBuilder.create(context).run {
        addNextIntentWithParentStack(intent)
        getPendingIntent(0, PendingIntent.FLAG_MUTABLE)
    }

    return NotificationCompat.Builder(context, channelId)
        .setContentTitle(title)
        .setContentText(message)
        .setSmallIcon(R.mipmap.ic_launcher_round)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .setContentIntent(pendingIntent)
        .build()
}

@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel(
    notificationManager: NotificationManager,
    channelId: String
) {
    val channel = NotificationChannel(
        channelId,
        channelId,
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        description = "A Firebase TODO list"
        enableLights(true)
        lightColor = Color.GREEN
    }
    notificationManager.createNotificationChannel(channel)
}