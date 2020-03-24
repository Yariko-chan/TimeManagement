package com.ganeeva.d.f.timemanagement.core

import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.*


const val TASK_START_TIME = "TASK_START_TIME_FORMAT"
const val TASK_DATE = "TASK_DATE_FORMAT"
const val DURATION = "DURATION_FORMAT"

const val TASK_START_TIME_FORMAT = "dd.MM.yyyy HH:mm"
const val TASK_START_TIME_SEC_FORMAT = "dd.MM.yyyy HH:mm:ss"
const val TASK_DATE_FORMAT = "dd.MM.yyyy HH:mm"
const val DURATION_FORNAT = "HH:mm:ss"

val appModule = module {
    single(named(TASK_DATE)) { SimpleDateFormat(TASK_START_TIME_FORMAT, Locale.ROOT) }
    single(named(TASK_START_TIME)) { SimpleDateFormat(TASK_DATE_FORMAT, Locale.ROOT) }
    single(named(DURATION)) { SimpleDateFormat(DURATION_FORNAT, Locale.ROOT) }
}