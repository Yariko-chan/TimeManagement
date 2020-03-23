package com.ganeeva.d.f.timemanagement.tmp.full_task.domain.model

import androidx.lifecycle.LiveData

abstract class Task(
    val id: Long,
    val name: String,
    val description: String = "",
    val creationDate: Long,
    val duration: LiveData<Long>
)