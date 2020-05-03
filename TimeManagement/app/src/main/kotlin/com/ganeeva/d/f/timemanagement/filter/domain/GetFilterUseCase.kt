package com.ganeeva.d.f.timemanagement.filter.domain

class GetFilterUseCase(
    private val filtersRepository: FiltersRepository
) {

    operator fun invoke() : Filter {
        return filtersRepository.getFilter()
    }
}