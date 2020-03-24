package com.ganeeva.d.f.timemanagement.task.domain.model.subtask

fun SubTask.isRunning(): Boolean {
    return timeGaps.value?.isNotEmpty() == true && timeGaps.value?.last()?.endTime == null
}