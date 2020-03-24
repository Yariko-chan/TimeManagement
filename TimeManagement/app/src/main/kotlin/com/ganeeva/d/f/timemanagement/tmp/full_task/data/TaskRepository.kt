package com.ganeeva.d.f.timemanagement.tmp.full_task.data

import com.ganeeva.d.f.timemanagement.tmp.full_task.domain.model.Task

interface TaskRepository {
    fun getTask(id: Long) : Task
    fun getAll() : List<Task>
}