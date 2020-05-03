package com.ganeeva.d.f.timemanagement.filter.data

import com.ganeeva.d.f.timemanagement.filter.domain.Filter
import com.ganeeva.d.f.timemanagement.filter.domain.FiltersRepository
import com.ganeeva.d.f.timemanagement.filter.domain.SortType

class CacheFiltersRepository(
    private val filtersCache: FiltersCache
): FiltersRepository {
    override fun getFilter(): Filter {
        return Filter(
            sortType = filtersCache.sortType,
            periodStart = filtersCache.periodStart,
            periodEnd = filtersCache.periodEnd
        )
    }

    override fun saveSort(sortType: SortType) {
        filtersCache.sortType = sortType
    }

    override fun savePeriodStart(start: Long) {
        filtersCache.periodStart = start
    }

    override fun savePeriodEnd(end: Long) {
        filtersCache.periodEnd = end
    }
}