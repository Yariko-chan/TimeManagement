package com.ganeeva.d.f.timemanagement.task_view.domain

import com.ganeeva.d.f.timemanagement.core.domain.ResultUseCase
import com.ganeeva.d.f.timemanagement.core.domain.Either
import com.ganeeva.d.f.timemanagement.task.domain.Task
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository
import kotlinx.coroutines.Dispatchers

class GetTaskByIdUseCase(
    private val taskRepository: TaskRepository
): ResultUseCase<Long, Task>(Dispatchers.IO) {

    override suspend fun run(params: Long): Either<Task, Throwable> {
        return try {
            Either.Left(taskRepository.getTask(params))
        } catch (e: Throwable) {
            Either.Right(e)
        }
    }

}