package com.ganeeva.d.f.timemanagement.notification

import android.app.NotificationManager
import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

const val DEFAULT_CHANNEL_ID = "com.ganeeva.d.f.timemanagement.notification"
const val DEFAULT_CHANNEL_NAME = "default_channel"

val notificationModule = module {
    single { DefaultNotificationHelper(
        androidContext(),
        DEFAULT_CHANNEL_ID,
        DEFAULT_CHANNEL_NAME,
        get()
    ) as NotificationHelper }

    single { androidContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }
}