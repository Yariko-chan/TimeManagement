package com.ganeeva.d.f.timemanagement.task_list.domain

import com.ganeeva.d.f.timemanagement.core.domain.ResultUseCase
import com.ganeeva.d.f.timemanagement.core.domain.Either
import com.ganeeva.d.f.timemanagement.core.domain.EmptyParam
import com.ganeeva.d.f.timemanagement.task.domain.Task
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository
import kotlinx.coroutines.Dispatchers

class GetTaskUseCase(
    private val taskRepository: TaskRepository
): ResultUseCase<EmptyParam, List<Task>>(Dispatchers.IO) {

    override suspend fun run(params: EmptyParam): Either<List<Task>, Throwable> {
        try {
            val tasks = taskRepository.getAll()
            return Either.Left(tasks)
        } catch (e: Exception) {
            return Either.Right(e)
        }
    }
}