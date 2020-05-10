package com.ganeeva.d.f.timemanagement.task_list.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.core.SingleLiveEvent
import com.ganeeva.d.f.timemanagement.core.domain.EmptyParam
import com.ganeeva.d.f.timemanagement.task_list.domain.GetTaskListUseCase
import com.ganeeva.d.f.timemanagement.task_running.TimeGapInteractor
import com.ganeeva.d.f.timemanagement.task_time_service.NotificationData
import com.ganeeva.d.f.timemanagement.task.domain.model.task.StandaloneTask
import com.ganeeva.d.f.timemanagement.task.domain.model.task.Task
import com.ganeeva.d.f.timemanagement.task.domain.model.task.isRunning
import com.ganeeva.d.f.timemanagement.task_list.domain.SearchTaskUseCase

abstract class TaskListViewModel : ViewModel() {

    abstract val progressLiveData: LiveData<Boolean>
    abstract val tasksListLiveData: LiveData<List<Task>>
    abstract val emptyListLiveData: LiveData<Unit>
    abstract val errorEvent: SingleLiveEvent<Int>
    abstract val showTaskEvent: SingleLiveEvent<Long>
    abstract val runLiveData: LiveData<NotificationData>
    abstract val stopLiveData: LiveData<Unit>
    abstract val errorLiveData: SingleLiveEvent<Int>
    abstract val showFilterEvent: LiveData<Unit>

    abstract fun onViewVisible()
    abstract fun onTaskClicked(position: Int, task: Task)
    abstract fun onTaskChecked(task: Task, isChecked: Boolean)
    abstract fun onFilterClicked()
    abstract fun onQueryClear()
    abstract fun onNewQuery(query: String)
}

class DefaultTaskListViewModel(
    private val getTaskListUseCase: GetTaskListUseCase,
    private val searchTaskUseCase: SearchTaskUseCase,
    private val timeGapInteractor: TimeGapInteractor
): TaskListViewModel() {

    override val progressLiveData = MutableLiveData<Boolean>()
    override val tasksListLiveData = MutableLiveData<List<Task>>()
    override val emptyListLiveData = MutableLiveData<Unit>()
    override val errorEvent = SingleLiveEvent<Int>()
    override val showTaskEvent = SingleLiveEvent<Long>()
    override val runLiveData = MutableLiveData<NotificationData>()
    override val stopLiveData = MutableLiveData<Unit>()
    override val errorLiveData = SingleLiveEvent<Int>()
    override val showFilterEvent = SingleLiveEvent<Unit>()

    override fun onViewVisible() {
        loadData()
    }

    override fun onTaskClicked(position: Int, task: Task) {
        showTaskEvent.value = task.id
    }

    override fun onTaskChecked(task: Task, isChecked: Boolean) {
        when {
            isChecked && !task.isRunning() -> runTask(task)
            !isChecked && task.isRunning() -> stopRunningTask(task)
        }
    }

    override fun onFilterClicked() {
        showFilterEvent.value = Unit
    }

    override fun onNewQuery(query: String) {
        searchTaskUseCase(viewModelScope, query) { it.fold(::onTaskList, ::onError) }
    }

    override fun onQueryClear() {
        loadData()
    }

    private fun loadData() {
        progressLiveData.value = true
        getTaskListUseCase(viewModelScope, EmptyParam) { it.fold(::onTaskList, ::onError) }
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
        errorEvent.value = R.string.error_get_task_list
    }

    private fun runTask(task: Task) {
        if (task is StandaloneTask) {
            timeGapInteractor.startTask(viewModelScope, task.id,
                onSuccess = { runLiveData.value = NotificationData(task.id, task.name) },
                onError = { errorLiveData.value = R.string.error_task_running} )
        } else {
            errorLiveData.value = R.string.error_task_has_subtask
        }
    }

    private fun stopRunningTask(task: Task) {
        if (task is StandaloneTask) {
            timeGapInteractor.stopTask(viewModelScope, task.id,
                onSuccess = { stopLiveData.value = Unit },
                onError = { errorLiveData.value = R.string.error_task_stopping} )
        } else {
            errorLiveData.value = R.string.error_task_has_subtask
        }
    }
}