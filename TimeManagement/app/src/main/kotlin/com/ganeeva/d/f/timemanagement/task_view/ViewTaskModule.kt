package com.ganeeva.d.f.timemanagement.task_view

import com.ganeeva.d.f.timemanagement.core.DURATION
import com.ganeeva.d.f.timemanagement.core.TASK_DATE
import com.ganeeva.d.f.timemanagement.task_view.domain.GetTaskUseCase
import com.ganeeva.d.f.timemanagement.task_view.domain.RemoveTaskUseCase
import com.ganeeva.d.f.timemanagement.task_running.DefaultTimeGapInteractor
import com.ganeeva.d.f.timemanagement.task_running.StartTaskUseCase
import com.ganeeva.d.f.timemanagement.task_running.StopTaskUseCase
import com.ganeeva.d.f.timemanagement.task_running.TimeGapInteractor
import com.ganeeva.d.f.timemanagement.task_view.ui.DefaultViewTaskViewModel
import com.ganeeva.d.f.timemanagement.task_view.ui.ViewTaskViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewTaskModule = module {
    viewModel{ DefaultViewTaskViewModel(
        get(),
        get(),
        get(),
        get(named(TASK_DATE)),
        get(named(DURATION))
    ) as ViewTaskViewModel
    }
    factory { GetTaskUseCase( get()) }
    factory { RemoveTaskUseCase( get() ) }
}