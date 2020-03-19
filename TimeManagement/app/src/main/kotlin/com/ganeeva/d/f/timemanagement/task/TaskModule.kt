package com.ganeeva.d.f.timemanagement.task

import com.ganeeva.d.f.timemanagement.task.data.*
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository
import org.koin.dsl.module

val taskModule = module {
    single { DbTaskRepository(get(), get(), get() ) as TaskRepository}
    factory { NewTaskMapper() }
    factory { DbTaskMapper( get(), get()) }
    factory { DbSubTaskMapper() }
    factory { DbTimeGapMapper() }
}