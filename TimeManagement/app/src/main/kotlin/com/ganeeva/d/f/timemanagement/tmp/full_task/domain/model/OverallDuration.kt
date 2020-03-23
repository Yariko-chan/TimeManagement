package com.ganeeva.d.f.timemanagement.tmp.full_task.domain.model

import androidx.lifecycle.MediatorLiveData

class OverallDuration(
    private val subtasks: List<SubTask>
) : MediatorLiveData<Long>() {

    init {
        subtasks.forEach { addSource(it.duration, { onChanged() }) }
    }

    private fun onChanged() {
        var newDuration: Long = 0
        subtasks.forEach {
            newDuration += it.duration.value ?: 0
        }
        value = newDuration
    }
}