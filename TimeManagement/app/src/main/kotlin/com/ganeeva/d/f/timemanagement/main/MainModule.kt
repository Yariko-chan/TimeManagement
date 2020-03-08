package com.ganeeva.d.f.timemanagement.main

import com.ganeeva.d.f.timemanagement.main.ui.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    viewModel { MainViewModel() }
}