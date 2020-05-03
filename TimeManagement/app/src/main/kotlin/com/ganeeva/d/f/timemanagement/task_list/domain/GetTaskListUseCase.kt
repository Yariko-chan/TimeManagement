package com.ganeeva.d.f.timemanagement.task_list.domain

import com.ganeeva.d.f.timemanagement.core.domain.ResultUseCase
import com.ganeeva.d.f.timemanagement.core.domain.Either
import com.ganeeva.d.f.timemanagement.core.domain.EmptyParam
import com.ganeeva.d.f.timemanagement.filter.domain.FiltersRepository
import com.ganeeva.d.f.timemanagement.filter.domain.SortType
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository
import com.ganeeva.d.f.timemanagement.task.domain.model.task.Task
import kotlinx.coroutines.Dispatchers

class GetTaskListUseCase(
    private val taskRepository: TaskRepository,
    private val filtersRepository: FiltersRepository
): ResultUseCase<EmptyParam, List<Task>>(Dispatchers.IO) {

    override suspend fun run(params: EmptyParam): Either<List<Task>, Throwable> {
        return try {
            val filter = filtersRepository.getFilter()
            val tasks: List<Task> = when (filter.sortType) {
                SortType.ALPHABET -> taskRepository.getAllSortedAlphabetically(filter.periodStart, filter.periodEnd)
                SortType.CREATION_DATE_NEW -> taskRepository.getAllSortedByCreationDate(true, filter.periodStart, filter.periodEnd)
                SortType.CREATION_DATE_OLD -> taskRepository.getAllSortedByCreationDate(false, filter.periodStart, filter.periodEnd)
                SortType.LENGTH_SHORT -> taskRepository.getAllSortedByLength(true, filter.periodStart, filter.periodEnd)
                SortType.LENGTH_LONG -> taskRepository.getAllSortedByLength(false, filter.periodStart, filter.periodEnd)
            }
            Either.Left(tasks)
        } catch (e: Exception) {
            Either.Right(e)
        }
    }
}