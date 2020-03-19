package com.ganeeva.d.f.timemanagement.core

import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.*


val TASK_DATE_FORMAT = "TASK_DATE_FORMAT"
val DURATION_FORMAT = "DURATION_FORMAT"

val appModule = module {
    single(named(TASK_DATE_FORMAT)) { SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT) }
    single(named(DURATION_FORMAT)) { SimpleDateFormat("HH:mm:ss", Locale.ROOT) }
}