package com.ganeeva.d.f.timemanagement.filter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ganeeva.d.f.timemanagement.filter.domain.*
import java.text.SimpleDateFormat
import java.util.*

abstract class FiltersViewModel : ViewModel() {
    abstract val sortType: LiveData<SortType>
    abstract val periodStart: LiveData<Calendar>
    abstract val periodEnd: LiveData<Calendar>
    abstract val periodStartDateText: LiveData<String>
    abstract val periodStartTimeText: LiveData<String>
    abstract val periodEndDateText: LiveData<String>
    abstract val periodEndTimeText: LiveData<String>
    abstract val finishLiveData: LiveData<Unit>

    abstract fun onBackClicked()
    abstract fun onSaveClicked()
    abstract fun onNewSortSelected(sortType: SortType)
    abstract fun onStartDate(year: Int, month: Int, dayOfMonth: Int)
    abstract fun onStartTime(hourOfDay: Int, minute: Int)
    abstract fun onEndDate(year: Int, month: Int, dayOfMonth: Int)
    abstract fun onEndTime(hourOfDay: Int, minute: Int)
}

class DefaultFiltersViewModel(
    private val dateFormat: SimpleDateFormat,
    private val timeFormat: SimpleDateFormat,
    private val getFilterUseCase: GetFilterUseCase,
    private val saveFiltersUseCase: SaveFiltersUseCase
): FiltersViewModel() {

    override val sortType = MutableLiveData<SortType>()
    override val periodStart = MutableLiveData<Calendar>()
    override val periodEnd = MutableLiveData<Calendar>()
    override val periodStartDateText = MediatorLiveData<String>()
        .apply { addSource(periodStart) {
            value = dateFormat.format(Date(it.timeInMillis))
        } }
    override val periodStartTimeText = MediatorLiveData<String>()
        .apply { addSource(periodStart) {
            value = timeFormat.format(Date(it.timeInMillis))
        } }
    override val periodEndDateText = MediatorLiveData<String>()
        .apply { addSource(periodEnd) {
            value = dateFormat.format(Date(it.timeInMillis))
        } }
    override val periodEndTimeText = MediatorLiveData<String>()
        .apply { addSource(periodEnd) {
            value = timeFormat.format(Date(it.timeInMillis))
        } }
    override val finishLiveData = MutableLiveData<Unit>()

    init {
        val filter = getFilterUseCase.invoke()
        sortType.value = filter.sortType
        periodStart.value = Calendar.getInstance().apply { timeInMillis = filter.periodStart }
        periodEnd.value = Calendar.getInstance().apply { timeInMillis = filter.periodEnd }
    }

    override fun onBackClicked() {
        finish()
    }

    override fun onSaveClicked() {
        saveFiltersUseCase.invoke(
            sortType = sortType.value,
            periodStart = periodStart.value?.timeInMillis,
            periodEnd = periodEnd.value?.timeInMillis)
        finish()
    }

    override fun onNewSortSelected(sortType: SortType) {
        this.sortType.value = sortType
    }
    override fun onStartDate(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = periodStart.value ?: Calendar.getInstance()
        calendar.setDate(year, month, dayOfMonth)
        periodStart.value = calendar
    }

    override fun onStartTime(hourOfDay: Int, minute: Int) {
        val calendar = periodStart.value ?: Calendar.getInstance()
        calendar.setTime(hourOfDay, minute)
        periodStart.value = calendar
    }

    override fun onEndDate(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = periodEnd.value ?: Calendar.getInstance()
        calendar.setDate(year, month, dayOfMonth)
        periodEnd.value = calendar
    }

    override fun onEndTime(hourOfDay: Int, minute: Int) {
        val calendar = periodEnd.value ?: Calendar.getInstance()
        calendar.setTime(hourOfDay, minute)
        periodEnd.value = calendar
    }

    private fun Calendar.setDate(year: Int, month: Int, dayOfMonth: Int) {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, dayOfMonth)
    }

    private fun Calendar.setTime(hourOfDay: Int, minute: Int) {
        set(Calendar.HOUR_OF_DAY, hourOfDay)
        set(Calendar.MINUTE, minute)
    }

    private fun finish() {
        finishLiveData.value = Unit
    }
}