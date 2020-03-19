package com.ganeeva.d.f.timemanagement.task.data

import com.ganeeva.d.f.timemanagement.db.task.DbTask
import com.ganeeva.d.f.timemanagement.db.time_gap.DbTimeGap
import com.ganeeva.d.f.timemanagement.task.domain.Task

class DbTaskMapper(
    private val dbSubTaskMapper: DbSubTaskMapper,
    private val dbTimeGapMapper: DbTimeGapMapper
) {

    fun map(
        mainTask: DbTask,
        subtasks: List<DbTask>,
        timeGaps: List<DbTimeGap>,
        duration: Long
    ): Task {
        return Task(
            mainTask.id,
            mainTask.name,
            mainTask.description,
            mainTask.creationDate,
            dbSubTaskMapper.map(subtasks),
            dbTimeGapMapper.map(timeGaps),
            duration
        )
    }
}