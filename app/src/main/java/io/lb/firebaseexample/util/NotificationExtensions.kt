package io.lb.firebaseexample.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import io.lb.firebaseexample.R
import io.lb.firebaseexample.splash_feature.presentation.SplashActivity
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.S)
fun NotificationManager?.notifyRemoteMessage(context: Context, channelId: String, message: RemoteMessage) {
    val intent = Intent(context, SplashActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

    val pendingIntent = TaskStackBuilder.create(context).run {
        addNextIntentWithParentStack(intent)
        getPendingIntent(0, PendingIntent.FLAG_MUTABLE)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setContentTitle(message.data["title"])
        .setContentText(message.data["message"])
        .setSmallIcon(R.mipmap.ic_launcher_round)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .setContentIntent(pendingIntent)
        .build()

    this?.notify(Random.nextInt(), notification)
}