package com.ganeeva.d.f.timemanagement.new_task.domain

import com.ganeeva.d.f.timemanagement.core.domain.ResultUseCase
import com.ganeeva.d.f.timemanagement.core.domain.Either
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository
import kotlinx.coroutines.Dispatchers

class CreateTaskUseCase(
    private val taskRepository: TaskRepository
): ResultUseCase<NewTask, Unit>(Dispatchers.IO) {

    override suspend fun run(params: NewTask): Either<Unit, Throwable> {
        return try {
            taskRepository.saveTask(params)
            Either.Left(Unit)
        } catch (e: Throwable) {
            Either.Right(e)
        }
    }
}