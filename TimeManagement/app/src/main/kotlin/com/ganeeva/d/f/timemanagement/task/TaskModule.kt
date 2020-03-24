package com.ganeeva.d.f.timemanagement.task

import com.ganeeva.d.f.timemanagement.task.data.DbTaskRepository
import com.ganeeva.d.f.timemanagement.task.data.mappers.*
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository
import org.koin.dsl.module

val taskModule = module {
    factory { NewTaskMapper() }
    factory { DbTimeGapMapper() }
    factory { StandaloneTaskMapper() }
    factory { SteppedTaskMapper() }
    factory { SubTaskMapper() }
    factory { DbTaskRepository( get(), get(), get(), get(), get(), get() ) as TaskRepository }
}