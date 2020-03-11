package com.ganeeva.d.f.timemanagement.task.data

import com.ganeeva.d.f.timemanagement.db.TaskDatabase
import com.ganeeva.d.f.timemanagement.task.domain.Task
import com.ganeeva.d.f.timemanagement.task.domain.TaskDataSource

class TaskRepository(
    private val db: TaskDatabase,
    private val taskMapper: TaskMapper,
    private val dbTaskMapper: DbTaskMapper
) : TaskDataSource {

    override fun getTask(id: Long) : Task {
        return dbTaskMapper.map(db.taskDao.getById(id))
    }

    override fun saveTask(task: Task): Long {
        return db.taskDao.insertTask(taskMapper.map(task))
    }

    override fun getAll() : List<Task> {
        return dbTaskMapper.map(db.taskDao.getAll())
    }
}