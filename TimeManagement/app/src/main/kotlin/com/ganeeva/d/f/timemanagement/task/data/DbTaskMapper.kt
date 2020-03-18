package com.ganeeva.d.f.timemanagement.task.data

import com.ganeeva.d.f.timemanagement.core.Mapper
import com.ganeeva.d.f.timemanagement.db.task.DbTask
import com.ganeeva.d.f.timemanagement.task.domain.SubTask
import com.ganeeva.d.f.timemanagement.task.domain.Task

class DbTaskMapper: Mapper<DbTask, Task>() {

    override fun map(from: DbTask): Task {
        return Task(
            from.id,
            from.name,
            from.description,
            from.creationDate)
    }

    fun mapSubTask(from: DbTask): SubTask {
        return SubTask(
            from.id,
            from.name)
    }

    fun map(mainTask: DbTask, subtasks: List<DbTask>): Task {
        return Task(
            mainTask.id,
            mainTask.name,
            mainTask.description,
            mainTask.creationDate,
            subtasks.map { mapSubTask(it) }
        )
    }

}