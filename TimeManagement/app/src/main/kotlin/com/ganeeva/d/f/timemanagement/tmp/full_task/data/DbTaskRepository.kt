package com.ganeeva.d.f.timemanagement.tmp.full_task.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.ganeeva.d.f.timemanagement.db.TaskDatabase
import com.ganeeva.d.f.timemanagement.db.task.DbTask
import com.ganeeva.d.f.timemanagement.tmp.full_task.mapper.DbTimeGapMapper
import com.ganeeva.d.f.timemanagement.tmp.full_task.domain.model.*

class DbTaskRepository(
    private val db: TaskDatabase,
    private val timeGapMapper: DbTimeGapMapper
) : TaskRepository {

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
        return StandaloneTask(
            mainTask.id,
            mainTask.name,
            mainTask.description,
            mainTask.creationDate,
            duration,
            gaps)
    }

    private fun getSteppedTask(mainTask: DbTask, dbSubtasks: List<DbTask>): SteppedTask {
        val subtasks = dbSubtasks.map { subtask ->
            val gaps = getGapsForTask(subtask.id)
            val duration = getDuration(gaps)
            SubTask(subtask.id, subtask.name, subtask.parentTaskId!!, duration, gaps)
        }
        val overallDuration = getOverallDuration(subtasks)
        return SteppedTask(
            mainTask.id,
            mainTask.name,
            mainTask.description,
            mainTask.creationDate,
            overallDuration,
            subtasks
        )
    }

    private fun getOverallDuration(subtasks: List<SubTask>): LiveData<Long> {
        return OverallDuration(subtasks)
    }

    private fun getGapsForTask(taskId: Long) =
        db.timeGapDao.getObservableTimeGapsForTask(taskId)
            .map { timeGapMapper.map(it) }

    private fun getDuration(timeGaps: LiveData<List<TimeGap>>): LiveData<Long> {
        return timeGaps.switchMap { gaps -> Duration(gaps) }
    }
}