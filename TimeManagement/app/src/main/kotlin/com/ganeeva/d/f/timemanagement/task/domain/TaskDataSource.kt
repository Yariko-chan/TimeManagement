package com.ganeeva.d.f.timemanagement.task.domain


interface TaskDataSource {
    fun getTask(id: Long) : Task
    fun saveTask(t: Task): Long
    fun getAll() : List<Task>
}