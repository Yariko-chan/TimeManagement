package com.ganeeva.d.f.timemanagement.task_view.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.core.SingleLiveEvent
import com.ganeeva.d.f.timemanagement.task.domain.Task
import com.ganeeva.d.f.timemanagement.task.domain.SubTask
import com.ganeeva.d.f.timemanagement.task_view.domain.GetTaskByIdUseCase
import com.ganeeva.d.f.timemanagement.task_view.domain.RemoveTaskUseCase
import java.text.SimpleDateFormat

abstract class ViewTaskViewModel : ViewModel() {

    abstract val nameLiveData: LiveData<String>
    abstract val descriptionLiveData: LiveData<String>
    abstract val dateLiveData: LiveData<String>
    abstract val subtasksLiveData: LiveData<List<SubTask>>
    abstract val errorLiveData: SingleLiveEvent<Int>
    abstract val finishLiveData: LiveData<Unit>

    abstract fun onTaskId(id: Long?)
    abstract fun onBackCliked()
    abstract fun onRemoveClicked()
}

class DefaultViewTaskViewModel(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val removeTaskUseCase: RemoveTaskUseCase,
    private val dateFormat: SimpleDateFormat
): ViewTaskViewModel() {

    private var task: Task? = null

    override val nameLiveData = MutableLiveData<String>()
    override val descriptionLiveData = MutableLiveData<String>()
    override val dateLiveData = MutableLiveData<String>()
    override val subtasksLiveData = MutableLiveData<List<SubTask>>()
    override val errorLiveData = SingleLiveEvent<Int>()
    override val finishLiveData = MutableLiveData<Unit>()

    override fun onTaskId(id: Long?) {
        when (id) {
            null -> showError()
            else -> loadTask(id)
        }
    }

    override fun onBackCliked() {
        finish()
    }

    override fun onRemoveClicked() {
        if (task == null) {
            errorLiveData.value = R.string.error_task_removing
        } else {
            removeTaskUseCase.invoke(viewModelScope, task!!.id) { it.fold(::onTaskRemoveSuccess, ::onTaskRemoveError) }
        }
    }

    private fun showError() {
        errorLiveData.value = R.string.error_task_not_found
    }

    private fun loadTask(id: Long) {
        getTaskByIdUseCase.invoke(viewModelScope, id) { it.fold(::onTaskLoadSuccess, ::onTaskLoadError) }
    }

    private fun onTaskLoadSuccess(task: Task) {
        this.task = task
        nameLiveData.value = task.name
        descriptionLiveData.value = task.description
        dateLiveData.value = dateFormat.format(task.creationDate)
        subtasksLiveData.value = task.subtasks
    }

    private fun onTaskRemoveSuccess(unit: Unit) {
        finish()
    }

    private fun onTaskLoadError(throwable: Throwable) {
        errorLiveData.value = R.string.error_task_not_found
    }

    private fun onTaskRemoveError(throwable: Throwable) {
        errorLiveData.value = R.string.error_task_removing
    }

    private fun finish() {
        finishLiveData.value = Unit
    }
}