package com.ganeeva.d.f.timemanagement.task_view.domain

import com.ganeeva.d.f.timemanagement.core.domain.Either
import com.ganeeva.d.f.timemanagement.core.domain.ResultUseCase
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository
import com.ganeeva.d.f.timemanagement.task.domain.model.task.Task
import kotlinx.coroutines.Dispatchers

class GetTaskUseCase(
    private val repository: TaskRepository
): ResultUseCase<Long, Task>(Dispatchers.IO) {

    override suspend fun run(params: Long): Either<Task, Throwable> {
        return try {
            Either.Left(repository.getTask(params))
        } catch (e: Throwable) {
            Either.Right(e)
        }
    }
}