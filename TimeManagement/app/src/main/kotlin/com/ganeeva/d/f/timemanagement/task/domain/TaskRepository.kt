package com.ganeeva.d.f.timemanagement.task.domain


interface TaskRepository {
    fun getTask(id: Long) : Task
    fun saveTask(task: Task, subtasks: List<Task>?): Long
    fun getAll() : List<Task>
}