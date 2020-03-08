package com.ganeeva.d.f.timemanagement.task_list

import com.ganeeva.d.f.timemanagement.task_list.domain.GetTaskUseCase
import com.ganeeva.d.f.timemanagement.main.ui.MainViewModel
import com.ganeeva.d.f.timemanagement.task_list.ui.TaskListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val taskListModule = module {
    viewModel { TaskListViewModel(get()) }
    factory { GetTaskUseCase( get() ) }
}