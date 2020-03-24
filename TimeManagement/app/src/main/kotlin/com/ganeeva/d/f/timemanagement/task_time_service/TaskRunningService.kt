package com.ganeeva.d.f.timemanagement.task_time_service

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.core.DURATION
import com.ganeeva.d.f.timemanagement.main.ui.MainActivity
import com.ganeeva.d.f.timemanagement.main.ui.MainActivity.Companion.TASK_ID
import com.ganeeva.d.f.timemanagement.notification.NotificationHelper
import com.ganeeva.d.f.timemanagement.task.domain.model.task.Task
import com.ganeeva.d.f.timemanagement.task_view.domain.GetTaskUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import java.text.SimpleDateFormat

const val SERVICE_NOTIFICATION_ID = 1010
const val MAIN_ACTIVITY_PENDING_INTENT = 1011

class TaskRunningService : LifecycleService() {

    companion object {
        const val ID = "id"
        const val NAME = "name"
        val TAG = TaskRunningService::class.java.simpleName

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
    private val getTaskUseCase: GetTaskUseCase by inject()
    private val durationFormat: SimpleDateFormat by inject(named(DURATION))
    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    private var id: Long = -1L

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val newId = intent?.getLongExtra(ID, -1L)
        if (newId != null && newId != id) {
            id = newId
            val name = intent.getStringExtra(NAME) ?: ""
            loadTask(id)

            val pi = createPendingIntent()
            val notification: Notification = notificationHelper.createNotification(name, "", pi)
            startForeground(SERVICE_NOTIFICATION_ID, notification)
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }

    private fun createPendingIntent(): PendingIntent {
        val mainActivityIntent = Intent(applicationContext, MainActivity::class.java)
        mainActivityIntent.putExtra(TASK_ID, id)
        return PendingIntent.getActivity(applicationContext, MAIN_ACTIVITY_PENDING_INTENT, mainActivityIntent, 0)
    }

    private fun loadTask(id: Long) {
        getTaskUseCase.invoke(serviceScope, id) { it.fold(::onTask, ::onError)}
    }

    private fun onTask(task: Task) {
        task.duration.observe(this, Observer {
            val pi = createPendingIntent()
            val notification: Notification = notificationHelper.createNotification(task.name, durationFormat.format(it), pi)
            notificationHelper.notify(SERVICE_NOTIFICATION_ID,notification)
        })
    }

    private fun onError(throwable: Throwable) {
        Log.e(TAG, "Can't load task: ${throwable.message}")
        throwable.printStackTrace()
    }
}
