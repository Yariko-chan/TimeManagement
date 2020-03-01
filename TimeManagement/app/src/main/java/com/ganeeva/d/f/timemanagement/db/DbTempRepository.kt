package com.ganeeva.d.f.timemanagement.db

import androidx.lifecycle.LiveData
import com.ganeeva.d.f.timemanagement.core.App
import com.ganeeva.d.f.timemanagement.db.task.Task

class DbTempRepository {
    private val db = TaskDatabase.getInstance(App.instance)

    fun getTask(id: Long) : Task = db.taskDao.getById(id)

    fun saveTask(t: Task): Long {
        return db.taskDao.insertTask(t)
    }
}