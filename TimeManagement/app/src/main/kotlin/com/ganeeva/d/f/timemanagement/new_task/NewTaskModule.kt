package com.ganeeva.d.f.timemanagement.new_task

import com.ganeeva.d.f.timemanagement.new_task.domain.CreateTaskUseCase
import com.ganeeva.d.f.timemanagement.new_task.ui.DefaultNewTaskViewModel
import com.ganeeva.d.f.timemanagement.new_task.ui.NewTaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newTaskModule = module {
    factory { CreateTaskUseCase( get() ) }
    viewModel{ DefaultNewTaskViewModel( androidContext(), get() ) as NewTaskViewModel }
}