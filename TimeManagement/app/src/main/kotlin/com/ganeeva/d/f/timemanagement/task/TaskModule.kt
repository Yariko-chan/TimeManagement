package com.ganeeva.d.f.timemanagement.task

import com.ganeeva.d.f.timemanagement.task.data.DbTaskMapper
import com.ganeeva.d.f.timemanagement.task.data.TaskMapper
import com.ganeeva.d.f.timemanagement.task.data.TaskRepository
import com.ganeeva.d.f.timemanagement.task.domain.TaskDataSource
import org.koin.dsl.module

val taskModule = module {
    single { TaskRepository(get(), get(), get() ) as TaskDataSource}
    factory { TaskMapper() }
    factory { DbTaskMapper() }
}