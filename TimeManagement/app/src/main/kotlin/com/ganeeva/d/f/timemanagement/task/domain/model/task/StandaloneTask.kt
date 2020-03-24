package com.ganeeva.d.f.timemanagement.task.domain.model.task

import androidx.lifecycle.LiveData
import com.ganeeva.d.f.timemanagement.task.domain.model.TimeGap

class StandaloneTask(
    id: Long,
    name: String,
    description: String = "",
    creationDate: Long,
    duration: LiveData<Long>,
    val timeGaps: LiveData<List<TimeGap>>
) : Task(id, name, description, creationDate, duration)