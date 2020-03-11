package com.ganeeva.d.f.timemanagement.task.data

import com.ganeeva.d.f.timemanagement.core.Mapper
import com.ganeeva.d.f.timemanagement.db.task.DbTask
import com.ganeeva.d.f.timemanagement.task.domain.Task

class DbTaskMapper: Mapper<DbTask, Task>() {

    override fun map(from: DbTask): Task {
        return Task(from.name, from.description)
    }

}