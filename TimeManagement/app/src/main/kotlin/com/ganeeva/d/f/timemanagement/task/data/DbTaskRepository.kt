package com.ganeeva.d.f.timemanagement.task.data

import com.ganeeva.d.f.timemanagement.db.TaskDatabase
import com.ganeeva.d.f.timemanagement.new_task.domain.NewTask
import com.ganeeva.d.f.timemanagement.task.domain.Task
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository

class DbTaskRepository(
    private val db: TaskDatabase,
    private val newTaskMapper: NewTaskMapper,
    private val dbTaskMapper: DbTaskMapper
) : TaskRepository {

    override fun getTask(id: Long) : Task {
        return dbTaskMapper.map(db.taskDao.getById(id))
    }

    override fun saveTask(task: NewTask) {
        val parentId = db.taskDao.insertTask(newTaskMapper.mapMainTask(task))
        newTaskMapper.mapSubTasks(task, parentId).forEach {
            db.taskDao.insertTask(it)
        }
    }

    override fun getAll() : List<Task> {
        val dbTasks = db.taskDao.getAllTasks()
        val tasks = mutableListOf<Task>()
        dbTasks.forEach { mainTask ->
            val subtasks = db.taskDao.getSubTasks(mainTask.taskId)
            tasks += dbTaskMapper.map(mainTask, subtasks)
        }
        return tasks
    }
}