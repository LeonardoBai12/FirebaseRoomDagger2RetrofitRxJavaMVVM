package io.lb.firebaseexample.notification_feature.data.data_source

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

class ScheduledNotificationDataSource {
    @RequiresApi(Build.VERSION_CODES.S)
    fun scheduleNotification(
        context: Context,
        title: String,
        message: String,
        day: Int,
        month: Int,
        year: Int,
    ) {
        val intent = Intent(context, NotificationBroadcast::class.java)
        intent.putExtra("titleExtra", title)
        intent.putExtra("messageExtra", message)

        val time = getTime(day, month, year)
        intent.putExtra("notificationId", time)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            time.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    private fun getTime(day: Int, month: Int, year: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, 13, 49)
        return calendar.timeInMillis
    }
}