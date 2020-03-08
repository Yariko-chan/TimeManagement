package com.ganeeva.d.f.timemanagement.task_list

import com.ganeeva.d.f.timemanagement.task_list.domain.GetTaskUseCase
import com.ganeeva.d.f.timemanagement.task_list.ui.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val taskListModule = module {
    viewModel { MainViewModel( get() ) }
    factory { GetTaskUseCase( get() ) }
}