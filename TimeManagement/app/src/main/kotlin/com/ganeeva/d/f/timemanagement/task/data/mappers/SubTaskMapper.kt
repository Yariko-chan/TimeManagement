package com.ganeeva.d.f.timemanagement.task.data.mappers

import androidx.lifecycle.LiveData
import com.ganeeva.d.f.timemanagement.db.task.DbTask
import com.ganeeva.d.f.timemanagement.task.domain.model.TimeGap
import com.ganeeva.d.f.timemanagement.task.domain.model.task.SubTask

class SubTaskMapper {

    fun map(
        subtask: DbTask,
        duration: LiveData<Long>,
        gaps: LiveData<List<TimeGap>>
    ) : SubTask {
        return SubTask(
            subtask.id,
            subtask.name,
            subtask.parentTaskId!!,
            duration,
            gaps
        )
    }
}