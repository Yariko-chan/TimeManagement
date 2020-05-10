package com.ganeeva.d.f.timemanagement.task_list

import com.ganeeva.d.f.timemanagement.task_list.domain.GetTaskListUseCase
import com.ganeeva.d.f.timemanagement.task_list.domain.SearchTaskUseCase
import com.ganeeva.d.f.timemanagement.task_list.ui.DefaultTaskListViewModel
import com.ganeeva.d.f.timemanagement.task_list.ui.TaskListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val taskListModule = module {
    viewModel { DefaultTaskListViewModel( get(), get(), get() ) as TaskListViewModel }
    factory { GetTaskListUseCase( get(), get() ) }
    factory { SearchTaskUseCase( get() ) }
}