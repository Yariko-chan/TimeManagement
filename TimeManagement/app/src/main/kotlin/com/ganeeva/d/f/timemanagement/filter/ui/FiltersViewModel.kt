package com.ganeeva.d.f.timemanagement.filter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ganeeva.d.f.timemanagement.filter.domain.*

abstract class FiltersViewModel : ViewModel() {
    abstract val sortType: LiveData<SortType>
    abstract val periodStart: LiveData<Long>
    abstract val periodEnd: LiveData<Long>
    abstract val finishLiveData: LiveData<Unit>

    abstract fun onBackClicked()
    abstract fun onSaveClicked()
    abstract fun onNewSortSelected(sortType: SortType)
}

class DefaultFiltersViewModel(
    private val getFilterUseCase: GetFilterUseCase,
    private val saveFiltersUseCase: SaveFiltersUseCase
): FiltersViewModel() {

    override val sortType = MutableLiveData<SortType>()
    override val periodStart = MutableLiveData<Long>()
    override val periodEnd = MutableLiveData<Long>()
    override val finishLiveData = MutableLiveData<Unit>()

    init {
        val filter = getFilterUseCase.invoke()
        sortType.value = filter.sortType
        periodStart.value = filter.periodStart
        periodEnd.value = filter.periodEnd
    }

    override fun onBackClicked() {
        finish()
    }

    override fun onSaveClicked() {
        saveFiltersUseCase.invoke(
            sortType = sortType.value,
            periodStart = periodStart.value,
            periodEnd = periodEnd.value)
        finish()
    }

    override fun onNewSortSelected(sortType: SortType) {
        this.sortType.value = sortType
    }

    private fun finish() {
        finishLiveData.value = Unit
    }
}