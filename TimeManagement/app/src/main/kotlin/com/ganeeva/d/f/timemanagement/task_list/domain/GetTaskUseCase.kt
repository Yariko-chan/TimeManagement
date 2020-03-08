package com.ganeeva.d.f.timemanagement.task_list.domain

import com.ganeeva.d.f.timemanagement.core.domain.BaseUseCase
import com.ganeeva.d.f.timemanagement.core.domain.Either
import com.ganeeva.d.f.timemanagement.core.domain.EmptyParam
import com.ganeeva.d.f.timemanagement.db.TaskRepository
import com.ganeeva.d.f.timemanagement.db.task.Task

class GetTaskUseCase(
    private val taskRepository: TaskRepository
): BaseUseCase<EmptyParam, List<Task>>() {



    override suspend fun run(params: EmptyParam): Either<List<Task>, Throwable> {
        try {
            val newTask = Task(name = " test", description = "description")
            val result = taskRepository.saveTask(newTask)
            val tasks = taskRepository.getAll()
            return Either.Left(tasks)
        } catch (e: Exception) {
            return Either.Right(e)
        }
    }
}