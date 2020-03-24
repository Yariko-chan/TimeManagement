package com.ganeeva.d.f.timemanagement.task_running

import org.koin.dsl.module

val taskRunningModule = module {
    factory { DefaultTimeGapInteractor( get(), get() ) as TimeGapInteractor }
    factory { StartTaskUseCase( get() ) }
    factory { StopTaskUseCase( get() ) }
}