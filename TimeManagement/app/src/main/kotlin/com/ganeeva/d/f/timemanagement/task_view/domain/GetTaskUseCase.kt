package com.ganeeva.d.f.timemanagement.task_view.domain

import com.ganeeva.d.f.timemanagement.core.domain.Either
import com.ganeeva.d.f.timemanagement.core.domain.ResultUseCase
import com.ganeeva.d.f.timemanagement.tmp.full_task.data.TaskRepository
import com.ganeeva.d.f.timemanagement.tmp.full_task.domain.model.Task
import kotlinx.coroutines.Dispatchers

class GetTaskUseCase(
    private val repository: TaskRepository
): ResultUseCase<Long, Task>(Dispatchers.IO) {

    override suspend fun run(params: Long): Either<Task, Throwable> {
        try {
            return Either.Left(repository.getTask(params))
        } catch (e: Throwable) {
            return Either.Right(e)
        }
    }
}