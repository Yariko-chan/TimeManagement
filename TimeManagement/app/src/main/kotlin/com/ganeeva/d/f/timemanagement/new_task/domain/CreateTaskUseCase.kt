package com.ganeeva.d.f.timemanagement.new_task.domain

import com.ganeeva.d.f.timemanagement.core.domain.BaseUseCase
import com.ganeeva.d.f.timemanagement.core.domain.Either
import com.ganeeva.d.f.timemanagement.task.domain.Task
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository
import kotlinx.coroutines.Dispatchers

class CreateTaskUseCase(
    private val taskRepository: TaskRepository
): BaseUseCase<CreateTaskUseCase.Param, Unit>(Dispatchers.IO) {

    override suspend fun run(param: Param): Either<Unit, Throwable> {
        try {
            taskRepository.saveTask(param.task, param.subtasks)
            return Either.Left(Unit)
        } catch (e: Throwable) {
            return Either.Right(e)
        }
    }

    class Param(
        val task: Task,
        val subtasks: List<Task>? = null)
}