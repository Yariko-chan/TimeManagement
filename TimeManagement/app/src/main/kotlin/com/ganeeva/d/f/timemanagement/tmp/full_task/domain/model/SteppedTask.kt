package com.ganeeva.d.f.timemanagement.tmp.full_task.domain.model

import androidx.lifecycle.LiveData

class SteppedTask(
    id: Long,
    name: String,
    description: String = "",
    creationDate: Long,
    duration: LiveData<Long>,
    val subtasks: List<SubTask>
) : Task(id, name, description, creationDate, duration)