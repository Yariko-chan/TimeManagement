package com.ganeeva.d.f.timemanagement.task.domain.model.task

import com.ganeeva.d.f.timemanagement.db.time_gap.TimeGapDao
import java.text.SimpleDateFormat

fun Task.isRunning(): Boolean {
    return when (this) {
        is StandaloneTask -> timeGaps.value?.isNotEmpty() == true && timeGaps.value?.last()?.endTime == null
        is SteppedTask -> subtasks.any { it.timeGaps.value?.isNotEmpty() == true && it.timeGaps.value?.last()?.endTime == null }
        else -> false
    }
}

fun Task.getDuration(timeGapDao: TimeGapDao): Long {
    return when (this) {
        is StandaloneTask -> {
            var sum = 0L
            timeGapDao.getAllForTask(id).forEach { sum += (it.endTime ?: System.currentTimeMillis()) - it.startTime }
            sum
        }
        is SteppedTask -> {
            var sum = 0L
            subtasks.forEach {
                timeGapDao.getAllForTask(it.id).forEach { sum += (it.endTime ?: System.currentTimeMillis()) - it.startTime }
            }
            sum
        }
        else -> 0L
    }
}