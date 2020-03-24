package com.ganeeva.d.f.timemanagement.task.domain.model.task

fun Task.isRunning(): Boolean {
    return when (this) {
        is StandaloneTask -> timeGaps.value?.isNotEmpty() == true && timeGaps.value?.last()?.endTime == null
        is SteppedTask -> subtasks.any { it.timeGaps.value?.isNotEmpty() == true && it.timeGaps.value?.last()?.endTime == null }
        else -> false
    }
}