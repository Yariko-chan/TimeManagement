package com.ganeeva.d.f.timemanagement.task_list.domain

import com.ganeeva.d.f.timemanagement.core.domain.ResultUseCase
import com.ganeeva.d.f.timemanagement.core.domain.Either
import com.ganeeva.d.f.timemanagement.core.domain.EmptyParam
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository
import com.ganeeva.d.f.timemanagement.task.domain.model.task.Task
import kotlinx.coroutines.Dispatchers

class GetAllTasksUseCase(
    private val taskRepository: TaskRepository
): ResultUseCase<EmptyParam, List<Task>>(Dispatchers.IO) {

    override suspend fun run(params: EmptyParam): Either<List<Task>, Throwable> {
        return try {
            val tasks = taskRepository.getAll()
            Either.Left(tasks)
        } catch (e: Exception) {
            Either.Right(e)
        }
    }
}