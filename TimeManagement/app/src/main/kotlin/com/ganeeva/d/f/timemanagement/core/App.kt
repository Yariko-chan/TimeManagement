package com.ganeeva.d.f.timemanagement.core

import android.app.Application
import com.ganeeva.d.f.timemanagement.db.dbModule
import com.ganeeva.d.f.timemanagement.main.mainModule
import com.ganeeva.d.f.timemanagement.task_list.taskListModule
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
                dbModule,
                mainModule,
                taskListModule
            )
        }
    }
}