package com.ganeeva.d.f.timemanagement.task.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.ganeeva.d.f.timemanagement.db.TaskDatabase
import com.ganeeva.d.f.timemanagement.db.task.DbTask
import com.ganeeva.d.f.timemanagement.new_task.domain.NewTask
import com.ganeeva.d.f.timemanagement.task.data.mappers.*
import com.ganeeva.d.f.timemanagement.task.domain.TaskRepository
import com.ganeeva.d.f.timemanagement.task.domain.model.duration.Duration
import com.ganeeva.d.f.timemanagement.task.domain.model.duration.OverallDuration
import com.ganeeva.d.f.timemanagement.task.domain.model.TimeGap
import com.ganeeva.d.f.timemanagement.task.domain.model.task.StandaloneTask
import com.ganeeva.d.f.timemanagement.task.domain.model.task.SteppedTask
import com.ganeeva.d.f.timemanagement.task.domain.model.task.SubTask
import com.ganeeva.d.f.timemanagement.task.domain.model.task.Task

class DbTaskRepository(
    private val db: TaskDatabase,
    private val newTaskMapper: NewTaskMapper,
    private val timeGapMapper: DbTimeGapMapper,
    private val standaloneTaskMapper: StandaloneTaskMapper,
    private val subTaskMapper: SubTaskMapper,
    private val steppedTaskMapper: SteppedTaskMapper
) : TaskRepository {

    override fun saveTask(task: NewTask) {
        val parentId = db.taskDao.insertTask(newTaskMapper.mapMainTask(task))
        newTaskMapper.mapSubTasks(task, parentId).forEach {
            db.taskDao.insertTask(it)
        }
    }

    override fun remove(id: Long) {
        db.taskDao.remove(id)
    }

    override fun getTask(id: Long) : Task {
        val mainTask = db.taskDao.getById(id)
        return getFullTaskInfo(mainTask)
    }

    override fun getAll() : List<Task> {
        val dbTasks = db.taskDao.getAllTasks()
        val tasks = mutableListOf<Task>()
        dbTasks.forEach { mainTask ->
            tasks +=getFullTaskInfo(mainTask)
        }
        return tasks
    }

    private fun getFullTaskInfo(mainTask: DbTask): Task {
        val subtasks = db.taskDao.getSubTasks(mainTask.id)
        return if (subtasks.isEmpty()) {
            getStandaloneTask(mainTask)
        } else {
            getSteppedTask(mainTask, subtasks)
        }
    }

    private fun getStandaloneTask(mainTask: DbTask): StandaloneTask {
        val gaps = getGapsForTask(mainTask.id)
        val duration = getDuration(gaps)
        return standaloneTaskMapper.map(mainTask, duration, gaps)
    }

    private fun getSteppedTask(mainTask: DbTask, dbSubtasks: List<DbTask>): SteppedTask {
        val subtasks = dbSubtasks.map { subtask ->
            val gaps = getGapsForTask(subtask.id)
            val duration = getDuration(gaps)
            subTaskMapper.map(subtask, duration, gaps)
        }
        val overallDuration = getOverallDuration(subtasks)
        return steppedTaskMapper.map(mainTask, overallDuration, subtasks)
    }

    private fun getOverallDuration(subtasks: List<SubTask>): LiveData<Long> {
        return OverallDuration(
            subtasks
        )
    }

    private fun getGapsForTask(taskId: Long) =
        db.timeGapDao.getObservableTimeGapsForTask(taskId)
            .map { timeGapMapper.map(it) }

    private fun getDuration(timeGaps: LiveData<List<TimeGap>>): LiveData<Long> {
        return timeGaps.switchMap { gaps ->
            Duration(
                gaps
            )
        }
    }
}