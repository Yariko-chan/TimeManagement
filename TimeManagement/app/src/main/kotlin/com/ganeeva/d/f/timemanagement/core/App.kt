package com.ganeeva.d.f.timemanagement.core

import android.app.Application
import com.ganeeva.d.f.timemanagement.db.dbModule
import com.ganeeva.d.f.timemanagement.main.mainModule
import com.ganeeva.d.f.timemanagement.new_task.newTaskModule
import com.ganeeva.d.f.timemanagement.notification.notificationModule
import com.ganeeva.d.f.timemanagement.task.taskModule
import com.ganeeva.d.f.timemanagement.task_list.taskListModule
import com.ganeeva.d.f.timemanagement.task_running.taskRunningModule
import com.ganeeva.d.f.timemanagement.task_view.viewTaskModule
import com.ganeeva.d.f.timemanagement.time_gap.timeGapModule
import com.ganeeva.d.f.timemanagement.tmp.full_task.fullTaskModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                appModule,
                dbModule,
                notificationModule,
                taskModule,
                timeGapModule,
                mainModule,
                taskListModule,
                newTaskModule,
                viewTaskModule,
                taskRunningModule,
                fullTaskModule // todo refactor
            )
        }
    }
}