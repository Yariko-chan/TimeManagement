package com.ganeeva.d.f.timemanagement.task_view.domain

import com.ganeeva.d.f.timemanagement.core.domain.BaseUseCase
import com.ganeeva.d.f.timemanagement.core.domain.Either
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository
import kotlinx.coroutines.Dispatchers

class RemoveTaskUseCase(
    private val taskRepository: TaskRepository
): BaseUseCase<Long, Unit>(Dispatchers.IO) {


    override suspend fun run(params: Long): Either<Unit, Throwable> {
        return try {
            Either.Left(taskRepository.remove(params))
        } catch (e: Throwable) {
            Either.Right(e)
        }
    }
}