package com.ganeeva.d.f.timemanagement.task.data.mappers

import androidx.lifecycle.LiveData
import com.ganeeva.d.f.timemanagement.db.task.DbTask
import com.ganeeva.d.f.timemanagement.task.domain.model.TimeGap
import com.ganeeva.d.f.timemanagement.task.domain.model.task.StandaloneTask

class StandaloneTaskMapper {

    fun map(
        mainTask: DbTask,
        duration: LiveData<Long>,
        gaps: LiveData<List<TimeGap>>
    ) : StandaloneTask {
        return StandaloneTask(
            mainTask.id,
            mainTask.name,
            mainTask.description,
            mainTask.creationDate,
            duration,
            gaps
        )
    }
}