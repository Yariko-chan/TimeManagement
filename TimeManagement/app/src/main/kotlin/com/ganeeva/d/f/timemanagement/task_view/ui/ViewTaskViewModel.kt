package com.ganeeva.d.f.timemanagement.task_view.ui

import android.util.Log
import androidx.lifecycle.*
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.core.SingleLiveEvent
import com.ganeeva.d.f.timemanagement.task_time_service.NotificationData
import com.ganeeva.d.f.timemanagement.task_view.domain.RemoveTaskUseCase
import com.ganeeva.d.f.timemanagement.task_view.domain.time_gap.TimeGapInteractor
import com.ganeeva.d.f.timemanagement.tmp.full_task.domain.model.*
import com.ganeeva.d.f.timemanagement.task_view.domain.GetTaskUseCase
import java.text.SimpleDateFormat

abstract class ViewTaskViewModel : ViewModel() {

    abstract val nameLiveData: LiveData<String>
    abstract val descriptionLiveData: LiveData<String>
    abstract val dateLiveData: LiveData<String>
    abstract val subtasksLiveData: LiveData<List<SubTask>>
    abstract val errorLiveData: SingleLiveEvent<Int>
    abstract val finishLiveData: LiveData<Unit>
    abstract val isRunningLiveData: LiveData<Boolean>
    abstract val runLiveData: LiveData<NotificationData>
    abstract val stopLiveData: LiveData<Unit>
    abstract val durationLiveData: LiveData<String>
    abstract val timeGapsLiveData: LiveData<List<TimeGap>>

    abstract fun onTaskId(id: Long?)
    abstract fun onBackCliked()
    abstract fun onRemoveClicked()
    abstract fun onRunChecked(isChecked: Boolean)
}

class DefaultViewTaskViewModel(
    private val getTaskUseCase: GetTaskUseCase,
    private val removeTaskUseCase: RemoveTaskUseCase,
    private val timeGapInteractor: TimeGapInteractor,
    private val taskDateFormat: SimpleDateFormat,
    private val taskDurationFormat: SimpleDateFormat
): ViewTaskViewModel() {

    private var task: Task? = null

    override val nameLiveData = MutableLiveData<String>()
    override val descriptionLiveData = MutableLiveData<String>()
    override val dateLiveData = MutableLiveData<String>()
    override val subtasksLiveData = MutableLiveData<List<SubTask>>()
    override val errorLiveData = SingleLiveEvent<Int>()
    override val finishLiveData = MutableLiveData<Unit>()
    override val isRunningLiveData = MediatorLiveData<Boolean>()
    override val runLiveData = MutableLiveData<NotificationData>()
    override val stopLiveData = MutableLiveData<Unit>()
    override val durationLiveData = MediatorLiveData<String>()
    override val timeGapsLiveData = MediatorLiveData<List<TimeGap>>()

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

    override fun onRunChecked(isChecked: Boolean) {
        when {
            isChecked && isRunningLiveData.value == false -> runTask()
            !isChecked && isRunningLiveData.value == true -> stopRunningTask()
        }
    }

    private fun showError() {
        errorLiveData.value = R.string.error_task_not_found
    }

    private fun loadTask(id: Long) {
        getTaskUseCase.invoke(viewModelScope, id) { it.fold(::onTaskLoadSuccess, ::onTaskLoadError) }
    }

    private fun onTaskLoadSuccess(task: Task) {
        this.task = task
        nameLiveData.value = task.name
        descriptionLiveData.value = task.description
        dateLiveData.value = taskDateFormat.format(task.creationDate)
        durationLiveData.addSource(task.duration) { it ->
            val format = taskDurationFormat.format(it)
            Log.d("Duration", "viewModel on new value $it = $format")
            durationLiveData.value = format
        }
        when (task) {
            is StandaloneTask -> showStandaloneTask(task)
            is SteppedTask -> showSteppedTask(task)
        }
    }

    private fun showStandaloneTask(task: StandaloneTask) {
        timeGapsLiveData.addSource(task.timeGaps) {
            timeGapsLiveData.value = task.timeGaps.value
        }
        isRunningLiveData.addSource(task.timeGaps) {
            isRunningLiveData.value = it.last().endTime == null
        }
    }

    private fun showSteppedTask(task: SteppedTask) {
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

    private fun runTask() {
        task?.run {
            timeGapInteractor.startTask(viewModelScope, id,
                onSuccess = { runLiveData.value = NotificationData(id, name) },
                onError = { errorLiveData.value = R.string.error_task_running} )
        }
    }

    private fun stopRunningTask() {
        task?.run {
            timeGapInteractor.stopTask(viewModelScope, id,
                onSuccess = { stopLiveData.value = Unit },
                onError = { errorLiveData.value = R.string.error_task_stopping} )
        }
    }
}