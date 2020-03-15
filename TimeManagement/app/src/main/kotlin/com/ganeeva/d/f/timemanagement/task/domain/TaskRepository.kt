package com.ganeeva.d.f.timemanagement.task.domain

import com.ganeeva.d.f.timemanagement.new_task.domain.NewTask


interface TaskRepository {
    fun getTask(id: Long) : Task
    fun saveTask(task: NewTask)
    fun getAll() : List<Task>
    fun remove(id: Long)
}