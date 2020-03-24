package com.ganeeva.d.f.timemanagement.task.data.mappers

import com.ganeeva.d.f.timemanagement.db.task.DbTask
import com.ganeeva.d.f.timemanagement.new_task.domain.NewTask

class NewTaskMapper {

    fun mapMainTask(from: NewTask): DbTask {
       return DbTask(
            name = from.name,
            description = from.description,
            creationDate = System.currentTimeMillis()
        )
    }

    fun mapSubTasks(from: NewTask, parentId: Long): List<DbTask> {
        val tasks = mutableListOf<DbTask>()
        from.subtasks?.forEach {
            tasks += DbTask(
                name= it,
                parentTaskId = parentId
            )
        }
        return tasks
    }
}