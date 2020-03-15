package com.ganeeva.d.f.timemanagement.task

import com.ganeeva.d.f.timemanagement.task.data.DbTaskMapper
import com.ganeeva.d.f.timemanagement.task.data.NewTaskMapper
import com.ganeeva.d.f.timemanagement.task.data.DbTaskRepository
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository
import org.koin.dsl.module

val taskModule = module {
    single { DbTaskRepository(get(), get(), get() ) as TaskRepository}
    factory { NewTaskMapper() }
    factory { DbTaskMapper() }
}