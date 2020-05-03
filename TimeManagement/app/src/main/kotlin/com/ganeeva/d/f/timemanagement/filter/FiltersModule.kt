package com.ganeeva.d.f.timemanagement.filter

import com.ganeeva.d.f.timemanagement.filter.data.CacheFiltersRepository
import com.ganeeva.d.f.timemanagement.filter.data.FiltersCache
import com.ganeeva.d.f.timemanagement.filter.domain.FiltersRepository
import com.ganeeva.d.f.timemanagement.filter.domain.GetFilterUseCase
import com.ganeeva.d.f.timemanagement.filter.domain.SaveFiltersUseCase
import com.ganeeva.d.f.timemanagement.filter.ui.DefaultFiltersViewModel
import com.ganeeva.d.f.timemanagement.filter.ui.FiltersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val filtersModule = module {
    viewModel { DefaultFiltersViewModel( get(), get() ) as FiltersViewModel }
    single { FiltersCache() }
    single { CacheFiltersRepository( get() ) as FiltersRepository }
    factory { GetFilterUseCase( get() ) }
    factory { SaveFiltersUseCase( get() ) }
}