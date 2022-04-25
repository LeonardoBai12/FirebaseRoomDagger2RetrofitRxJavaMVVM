package io.lb.firebaseexample.notification_feature.data.data_source

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

class ScheduledNotificationDataSource(
    private val context: Context,
) {
    @RequiresApi(Build.VERSION_CODES.S)
    fun scheduleNotification(
        title: String,
        day: Int,
        month: Int,
        year: Int,
    ) {
        val calendar = Calendar.getInstance(Locale.getDefault())

        listOf(9, 13, 19).forEach {
            val exactCalendar = getTime(day, month, year, it)

            if (exactCalendar.after(calendar)) {
                setExactTimeNotification(title, exactCalendar.timeInMillis)
            }
        }
    }

    private fun setExactTimeNotification(
        title: String,
        time: Long
    ) {
        val intent = Intent(context, NotificationBroadcast::class.java)

        intent.putExtra("titleExtra", "Existe uma tarefa pra hoje")
        intent.putExtra("messageExtra", title)
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

    private fun getTime(day: Int, month: Int, year: Int, hoursOfDay: Int): Calendar {
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.set(year, month, day, hoursOfDay, 0)
        return calendar
    }
}