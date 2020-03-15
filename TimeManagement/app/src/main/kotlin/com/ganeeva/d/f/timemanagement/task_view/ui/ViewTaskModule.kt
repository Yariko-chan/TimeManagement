package com.ganeeva.d.f.timemanagement.task_view.ui

import com.ganeeva.d.f.timemanagement.core.TASK_DATE_FORMAT
import com.ganeeva.d.f.timemanagement.task_view.domain.GetTaskByIdUseCase
import com.ganeeva.d.f.timemanagement.task_view.domain.RemoveTaskUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewTaskModule = module {
    viewModel{ DefaultViewTaskViewModel( get(),get(), get(named(TASK_DATE_FORMAT) ) ) as ViewTaskViewModel }
    factory { GetTaskByIdUseCase( get() ) }
    factory { RemoveTaskUseCase( get() ) }
}