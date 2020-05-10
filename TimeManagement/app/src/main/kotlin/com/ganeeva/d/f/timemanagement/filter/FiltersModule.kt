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
import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT = "dd.MM.yyyy"
const val TIME_FORMAT = "HH:mm"

val filtersModule = module {
    viewModel { DefaultFiltersViewModel(
        dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.ROOT),
        timeFormat = SimpleDateFormat(TIME_FORMAT, Locale.ROOT),
        getFilterUseCase = get(),
        saveFiltersUseCase = get() ) as FiltersViewModel }
    single { FiltersCache() }
    single { CacheFiltersRepository( get() ) as FiltersRepository }
    factory { GetFilterUseCase( get() ) }
    factory { SaveFiltersUseCase( get() ) }
}