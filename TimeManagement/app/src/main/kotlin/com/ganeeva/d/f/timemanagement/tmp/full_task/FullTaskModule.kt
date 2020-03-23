package com.ganeeva.d.f.timemanagement.tmp.full_task

import com.ganeeva.d.f.timemanagement.tmp.full_task.data.DbTaskRepository
import com.ganeeva.d.f.timemanagement.task_view.domain.GetTaskUseCase
import com.ganeeva.d.f.timemanagement.tmp.full_task.mapper.DbTimeGapMapper
import org.koin.dsl.module

// todo refactor
val fullTaskModule = module {
    factory { DbTaskRepository(get(), get()) }
    factory { DbTimeGapMapper() }
}