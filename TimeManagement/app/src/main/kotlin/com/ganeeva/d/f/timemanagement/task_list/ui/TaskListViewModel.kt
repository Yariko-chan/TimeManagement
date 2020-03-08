package com.ganeeva.d.f.timemanagement.task_list.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganeeva.d.f.timemanagement.core.domain.EmptyParam
import com.ganeeva.d.f.timemanagement.db.task.Task
import com.ganeeva.d.f.timemanagement.task_list.domain.GetTaskUseCase
import kotlinx.coroutines.Dispatchers

class TaskListViewModel(
    private val getTaskUseCase: GetTaskUseCase
): ViewModel() {

    val tasksList = MutableLiveData<List<Task>>()

    init {
        loadData()
    }

    private fun loadData() {
        getTaskUseCase(viewModelScope, Dispatchers.IO, EmptyParam) { it.fold(::handleFailure, ::handleMovieDetails) }
    }

    private fun handleFailure(tasks: List<Task>) {
        tasksList.value = tasks
    }

    private fun handleMovieDetails(throwable: Throwable) {
        val i = 5
    }
}