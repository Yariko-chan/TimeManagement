package com.ganeeva.d.f.timemanagement.db

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single { TaskDatabase.getInstance(androidContext()) }
}