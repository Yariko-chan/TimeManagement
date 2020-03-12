package com.ganeeva.d.f.timemanagement.task_list.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.core.SingleLiveEvent
import com.ganeeva.d.f.timemanagement.core.domain.EmptyParam
import com.ganeeva.d.f.timemanagement.task.domain.Task
import com.ganeeva.d.f.timemanagement.task_list.domain.GetTaskUseCase

abstract class TaskListViewModel : ViewModel() {

    abstract val progressLiveData: LiveData<Boolean>
    abstract val tasksListLiveData: LiveData<List<Task>>
    abstract val emptyListLiveData: LiveData<Unit>
    abstract val errorLiveData: SingleLiveEvent<Int>

    abstract fun onTaskClicked(position: Int, task: Task)
}

class DefaultTaskListViewModel(
    private val getTaskUseCase: GetTaskUseCase
): TaskListViewModel() {

    override val progressLiveData = MutableLiveData<Boolean>()
    override val tasksListLiveData = MutableLiveData<List<Task>>()
    override val emptyListLiveData = MutableLiveData<Unit>()
    override val errorLiveData = SingleLiveEvent<Int>()

    init {
        loadData()
    }

    override fun onTaskClicked(position: Int, task: Task) {
        // todo open here by id
    }

    private fun loadData() {
        progressLiveData.value = true
        getTaskUseCase(viewModelScope, EmptyParam) { it.fold(::onTaskList, ::onError) }
    }

    private fun onTaskList(list: List<Task>) {
        progressLiveData.value = false
        when {
            list.isEmpty() -> emptyListLiveData.value = Unit
            else -> tasksListLiveData.value = list
        }
    }

    private fun onError(throwable: Throwable) {
        progressLiveData.value = false
        emptyListLiveData.value = Unit
        errorLiveData.value = R.string.error_get_task_list
    }
}