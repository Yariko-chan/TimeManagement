package com.ganeeva.d.f.timemanagement.task.data

import com.ganeeva.d.f.timemanagement.db.TaskDatabase
import com.ganeeva.d.f.timemanagement.db.task.DbTask
import com.ganeeva.d.f.timemanagement.new_task.domain.NewTask
import com.ganeeva.d.f.timemanagement.task.domain.Task
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository

class DbTaskRepository(
    private val db: TaskDatabase,
    private val newTaskMapper: NewTaskMapper,
    private val dbTaskMapper: DbTaskMapper
) : TaskRepository {

    override fun getTask(id: Long) : Task {
        val mainTask = db.taskDao.getById(id)
        return mapTask(mainTask)
    }

    override fun getAll() : List<Task> {
        val dbTasks = db.taskDao.getAllTasks()
        val tasks = mutableListOf<Task>()
        dbTasks.forEach { mainTask ->
            tasks += mapTask(mainTask)
        }
        return tasks
    }

    override fun saveTask(task: NewTask) {
        val parentId = db.taskDao.insertTask(newTaskMapper.mapMainTask(task))
        newTaskMapper.mapSubTasks(task, parentId).forEach {
            db.taskDao.insertTask(it)
        }
    }

    override fun remove(id: Long) {
        db.taskDao.remove(id)
    }

    private fun mapTask(mainTask: DbTask): Task {
        val subtasks = db.taskDao.getSubTasks(mainTask.id)
        val timeGaps = db.timeGapDao.getAllForTask(mainTask.id)
        val duration = db.timeGapDao.getDuration(mainTask.id)
        return dbTaskMapper.map(mainTask, subtasks, timeGaps, duration)
    }
}