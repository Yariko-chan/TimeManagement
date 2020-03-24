package com.ganeeva.d.f.timemanagement.task.domain.model.task

import androidx.lifecycle.LiveData
import com.ganeeva.d.f.timemanagement.task.domain.model.subtask.SubTask

class SteppedTask(
    id: Long,
    name: String,
    description: String = "",
    creationDate: Long,
    duration: LiveData<Long>,
    val subtasks: List<SubTask>
) : Task(id, name, description, creationDate, duration)