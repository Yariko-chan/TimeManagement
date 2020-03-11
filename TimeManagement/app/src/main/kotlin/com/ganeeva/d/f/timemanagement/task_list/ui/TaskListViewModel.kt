package com.ganeeva.d.f.timemanagement.task_list.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganeeva.d.f.timemanagement.core.domain.EmptyParam
import com.ganeeva.d.f.timemanagement.task.domain.Task
import com.ganeeva.d.f.timemanagement.task_list.domain.GetTaskUseCase

class TaskListViewModel(
    private val getTaskUseCase: GetTaskUseCase
): ViewModel() {

    val tasksList = MutableLiveData<List<Task>>()

    init {
        loadData()
    }

    private fun loadData() {
        getTaskUseCase(viewModelScope, EmptyParam) { it.fold(::handleList, ::handleError) }
    }

    private fun handleList(dbTasks: List<Task>) {
        tasksList.value = dbTasks
    }

    private fun handleError(throwable: Throwable) {
        val i = 5
    }
}