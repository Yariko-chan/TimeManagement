package com.ganeeva.d.f.timemanagement.task.data

import com.ganeeva.d.f.timemanagement.core.Mapper
import com.ganeeva.d.f.timemanagement.db.task.DbTask
import com.ganeeva.d.f.timemanagement.task.domain.Task

class TaskMapper : Mapper<Task, DbTask>() {
    override fun map(from: Task): DbTask {
        return DbTask(
            name = from.name,
            description = from.description)
    }

    fun map(from: Task, parentId: Long): DbTask {
        return DbTask(
            name = from.name,
            description = from.description,
            parentTaskID = parentId)
    }
}