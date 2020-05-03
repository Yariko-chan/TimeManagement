package com.ganeeva.d.f.timemanagement.filter.domain

class SaveFiltersUseCase(
    private val filtersRepository: FiltersRepository
) {

    operator fun invoke(sortType: SortType?, periodStart: Long?, periodEnd: Long?) {
        sortType?.let { filtersRepository.saveSort(sortType) }
        periodStart?.let{ filtersRepository.savePeriodStart(periodStart) }
        periodEnd?.let{ filtersRepository.savePeriodEnd(periodEnd) }
    }
}