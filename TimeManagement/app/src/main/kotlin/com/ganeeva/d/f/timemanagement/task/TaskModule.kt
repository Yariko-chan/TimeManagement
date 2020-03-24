package com.ganeeva.d.f.timemanagement.task

import com.ganeeva.d.f.timemanagement.task.data.NewTaskMapper
import com.ganeeva.d.f.timemanagement.task.data.DbTaskRepository
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository
import com.ganeeva.d.f.timemanagement.task.data.DbTimeGapMapper
import org.koin.dsl.module

val taskModule = module {
    factory { NewTaskMapper() }
    factory { DbTimeGapMapper() }
    factory { DbTaskRepository( get(), get(), get() ) as TaskRepository }
}