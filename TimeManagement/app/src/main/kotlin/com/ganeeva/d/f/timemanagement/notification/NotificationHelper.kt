package com.ganeeva.d.f.timemanagement.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.ganeeva.d.f.timemanagement.R

interface NotificationHelper {

    fun createNotification(title: String, text: String, pendingIntent: PendingIntent): Notification
}

class DefaultNotificationHelper(
    private val applicationContext: Context,
    private val channelName: String,
    private val channelId: String,
    private val notificationManager: NotificationManager
) : NotificationHelper {

    override fun createNotification(title: String, text: String, pendingIntent: PendingIntent): Notification {
        createNotificationChannel()
        return NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_launcher_transparent)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
                notificationManager.getNotificationChannel(channelId) == null) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            notificationManager.createNotificationChannel(channel)
        }
    }

}