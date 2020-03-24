package com.ganeeva.d.f.timemanagement.task.data.mappers

import androidx.lifecycle.LiveData
import com.ganeeva.d.f.timemanagement.db.task.DbTask
import com.ganeeva.d.f.timemanagement.task.domain.model.task.SteppedTask
import com.ganeeva.d.f.timemanagement.task.domain.model.task.SubTask

class SteppedTaskMapper {

    fun map(
        mainTask: DbTask,
        overallDuration: LiveData<Long>,
        subtasks: List<SubTask>
    ) : SteppedTask {
        return SteppedTask(
            mainTask.id,
            mainTask.name,
            mainTask.description,
            mainTask.creationDate,
            overallDuration,
            subtasks
        )
    }
}