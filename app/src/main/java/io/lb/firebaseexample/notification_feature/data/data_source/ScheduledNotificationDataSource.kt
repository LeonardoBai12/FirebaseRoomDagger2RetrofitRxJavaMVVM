package io.lb.firebaseexample.notification_feature.data.data_source

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.SystemClock
import androidx.annotation.RequiresApi
import java.util.*

class ScheduledNotificationDataSource(
    private val context: Context,
) {
    @RequiresApi(Build.VERSION_CODES.S)
    fun scheduleNotification(
        title: String,
        message: String,
        day: Int,
        month: Int,
        year: Int,
    ) {
        val morning = getTime(day, month, year, 9)
        setExactTimeNotification(title, message, morning)
        val afternoon = getTime(day, month, year, 13)
        setExactTimeNotification(title, message, afternoon)
        val evening = getTime(day, month, year, 19)
        setExactTimeNotification(title, message, evening)
    }

    private fun setExactTimeNotification(
        title: String,
        message: String,
        time: Long
    ) {
        val intent = Intent(context, NotificationBroadcast::class.java)
        intent.putExtra("titleExtra", title)
        intent.putExtra("messageExtra", message)
        intent.putExtra("notificationId", time)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            time.toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    private fun getTime(day: Int, month: Int, year: Int, hoursOfDay: Int): Long {
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.set(year, month, day, hoursOfDay, 0)
        return calendar.timeInMillis
    }
}