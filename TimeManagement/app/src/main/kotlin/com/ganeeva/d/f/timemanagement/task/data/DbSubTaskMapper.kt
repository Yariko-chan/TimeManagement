package com.ganeeva.d.f.timemanagement.task.data

import com.ganeeva.d.f.timemanagement.core.Mapper
import com.ganeeva.d.f.timemanagement.db.task.DbTask
import com.ganeeva.d.f.timemanagement.task.domain.SubTask

class DbSubTaskMapper(): Mapper<DbTask, SubTask>() {
    override fun map(from: DbTask): SubTask {
        return SubTask(
            from.id,
            from.name)
    }
}