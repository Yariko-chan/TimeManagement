package com.ganeeva.d.f.timemanagement.task.domain

import com.ganeeva.d.f.timemanagement.new_task.domain.NewTask
import com.ganeeva.d.f.timemanagement.task.domain.model.task.Task

interface TaskRepository {
    fun saveTask(task: NewTask)
    fun remove(id: Long)
    fun getTask(id: Long) : Task
    fun getAll() : List<Task>
}