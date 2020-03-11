package com.ganeeva.d.f.timemanagement.task_list.domain

import com.ganeeva.d.f.timemanagement.core.domain.BaseUseCase
import com.ganeeva.d.f.timemanagement.core.domain.Either
import com.ganeeva.d.f.timemanagement.core.domain.EmptyParam
import com.ganeeva.d.f.timemanagement.task.domain.Task
import com.ganeeva.d.f.timemanagement.task.domain.TaskDataSource
import kotlinx.coroutines.Dispatchers

class GetTaskUseCase(
    private val taskDataSource: TaskDataSource
): BaseUseCase<EmptyParam, List<Task>>(Dispatchers.IO) {

    override suspend fun run(params: EmptyParam): Either<List<Task>, Throwable> {
        try {
            val tasks = taskDataSource.getAll()
            return Either.Left(tasks)
        } catch (e: Exception) {
            return Either.Right(e)
        }
    }
}