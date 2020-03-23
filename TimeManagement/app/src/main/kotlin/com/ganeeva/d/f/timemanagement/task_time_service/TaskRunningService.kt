package com.ganeeva.d.f.timemanagement.task_time_service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.annotation.Nullable
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.main.ui.MainActivity
import com.ganeeva.d.f.timemanagement.main.ui.MainActivity.Companion.TASK_ID
import com.ganeeva.d.f.timemanagement.notification.NotificationHelper
import org.koin.android.ext.android.inject


class TaskRunningService : Service() {

    companion object {
        val ID = "id"
        val NAME = "name"

        fun start(context: Context, id: Long, name: String) {
            val intent = Intent(context, TaskRunningService::class.java)
            intent.putExtra(ID, id)
            intent.putExtra(NAME, name)
            context.startService(intent)
        }

        fun stop(context: Context) {
            val intent = Intent(context, TaskRunningService::class.java)
            context.stopService(intent)
        }
    }

    private val notificationHelper: NotificationHelper by inject()

    var id: Long = -1L

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val newId = intent.getLongExtra(ID, -1L)
        if (newId != id) {
            id = newId
            val name = intent.getStringExtra(NAME) ?: ""

            val pi = createPendingIntent()
            val notification: Notification = notificationHelper.createNotification(getString(R.string.notification_task_running), name, pi)
            startForeground(1, notification)
        }

        return START_NOT_STICKY
    }

    private fun createPendingIntent(): PendingIntent {
        val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
        mainActivityIntent.putExtra(TASK_ID, id)
        val pi = PendingIntent.getActivity(applicationContext, 1011, mainActivityIntent, 0)
        return pi
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
