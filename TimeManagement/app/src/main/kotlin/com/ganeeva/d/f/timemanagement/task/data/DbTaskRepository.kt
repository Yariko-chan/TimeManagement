package com.ganeeva.d.f.timemanagement.task.data

import com.ganeeva.d.f.timemanagement.db.TaskDatabase
import com.ganeeva.d.f.timemanagement.task.domain.Task
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository

class DbTaskRepository(
    private val db: TaskDatabase,
    private val taskMapper: TaskMapper,
    private val dbTaskMapper: DbTaskMapper
) : TaskRepository {

    override fun getTask(id: Long) : Task {
        return dbTaskMapper.map(db.taskDao.getById(id))
    }

    override fun saveTask(task: Task, subtasks: List<Task>?): Long {
        val parentId = db.taskDao.insertTask(taskMapper.map(task))
        subtasks?.forEach {
            db.taskDao.insertTask(taskMapper.map(it, parentId))
        }
        return parentId
    }

    override fun getAll() : List<Task> {
        return dbTaskMapper.map(db.taskDao.getAll())
    }
}