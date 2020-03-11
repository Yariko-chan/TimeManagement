package com.ganeeva.d.f.timemanagement.new_task.domain

import com.ganeeva.d.f.timemanagement.core.domain.BaseUseCase
import com.ganeeva.d.f.timemanagement.core.domain.Either
import com.ganeeva.d.f.timemanagement.task.domain.Task
import com.ganeeva.d.f.timemanagement.task.domain.TaskDataSource
import kotlinx.coroutines.Dispatchers

class CreateTaskUseCase(
    private val taskDataSource: TaskDataSource
): BaseUseCase<Task, Unit>(Dispatchers.IO) {

    override suspend fun run(task: Task): Either<Unit, Throwable> {
        try {
            taskDataSource.saveTask(task)
            return Either.Left(Unit)
        } catch (e: Throwable) {
            return Either.Right(e)
        }
    }
}