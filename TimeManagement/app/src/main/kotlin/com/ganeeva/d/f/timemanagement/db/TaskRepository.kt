package com.ganeeva.d.f.timemanagement.db

import com.ganeeva.d.f.timemanagement.db.task.Task

class TaskRepository(
    private val db: TaskDatabase
) {

    fun getTask(id: Long) : Task = db.taskDao.getById(id)

    fun saveTask(t: Task): Long {
        return db.taskDao.insertTask(t)
    }

    fun getAll() : List<Task> = db.taskDao.getAll()
}