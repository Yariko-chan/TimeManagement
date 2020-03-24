package com.ganeeva.d.f.timemanagement.task.domain.model.duration

import androidx.lifecycle.MediatorLiveData
import com.ganeeva.d.f.timemanagement.task.domain.model.subtask.SubTask

class OverallDuration(
    private val subtasks: List<SubTask>
) : MediatorLiveData<Long>() {

    init {
        subtasks.forEach { addSource(it.duration) { onChanged() } }
    }

    private fun onChanged() {
        var newDuration: Long = 0
        subtasks.forEach {
            newDuration += it.duration.value ?: 0
        }
        value = newDuration
    }
}