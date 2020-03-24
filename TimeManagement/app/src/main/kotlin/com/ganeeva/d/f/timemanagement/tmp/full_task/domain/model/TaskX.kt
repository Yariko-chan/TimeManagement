package com.ganeeva.d.f.timemanagement.tmp.full_task.domain.model

fun Task.isRunning(): Boolean {
    return when (this) {
        is StandaloneTask -> timeGaps.value?.last()?.endTime == null
        is SteppedTask -> subtasks.any { it.timeGaps.value?.isNotEmpty() == true && it.timeGaps.value?.last()?.endTime == null }
        else -> false
    }
}