package com.ganeeva.d.f.timemanagement.filter.domain

interface FiltersRepository {

    fun getFilter() : Filter
    fun saveSort(sortType: SortType)
    fun savePeriodStart(start: Long)
    fun savePeriodEnd(end: Long)
}