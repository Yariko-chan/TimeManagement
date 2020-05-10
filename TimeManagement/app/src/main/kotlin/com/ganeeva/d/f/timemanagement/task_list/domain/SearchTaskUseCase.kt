package com.ganeeva.d.f.timemanagement.task_list.domain

import com.ganeeva.d.f.timemanagement.core.domain.Either
import com.ganeeva.d.f.timemanagement.core.domain.ResultUseCase
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository
import com.ganeeva.d.f.timemanagement.task.domain.model.task.Task
import kotlinx.coroutines.Dispatchers

class SearchTaskUseCase(
    private val taskRepository: TaskRepository
) : ResultUseCase<String, List<Task>>(Dispatchers.IO) {

    override suspend fun run(params: String): Either<List<Task>, Throwable> {
        return try {
            val tasks: List<Task> = taskRepository.searchTasks(params)
            Either.Left(tasks)
        } catch (e: Exception) {
            Either.Right(e)
        }
    }
}