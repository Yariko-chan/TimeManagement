package com.ganeeva.d.f.timemanagement.new_task.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.core.SingleLiveEvent
import com.ganeeva.d.f.timemanagement.new_task.domain.CreateTaskUseCase
import com.ganeeva.d.f.timemanagement.task.domain.Task

abstract class NewTaskViewModel: ViewModel() {

    abstract val nameErrorLiveData: LiveData<String?>
    abstract val finishLiveData: LiveData<Unit>
    abstract val errorLiveData: SingleLiveEvent<Int>
    abstract val showSubtaskDialogEvent: SingleLiveEvent<Unit>
    abstract val subtaskListLiveData: LiveData<List<String>>

    abstract fun onBackCLicked()
    abstract fun onOkClicked(name: String, description: String)
    abstract fun onAddSubtaskClicked()
    abstract fun onSubtaskDialogText(text: String)
}

class DefaultNewTaskViewModel(
    private val applicationContext: Context,
    private val createTaskUseCase: CreateTaskUseCase
): NewTaskViewModel() {

    override val nameErrorLiveData = MutableLiveData<String?>()
    override val finishLiveData = MutableLiveData<Unit>()
    override val errorLiveData = SingleLiveEvent<Int>()
    override val showSubtaskDialogEvent = SingleLiveEvent<Unit>()
    override val subtaskListLiveData = MutableLiveData<List<String>>()

    private val subtaskList = mutableListOf<String>()

    override fun onBackCLicked() {
        finish()
    }

    override fun onOkClicked(name: String, description: String) {
        validateName(name)
        if (isAllCorrect()) {
            save(name, description)
        }
    }

    override fun onAddSubtaskClicked() {
        showSubtaskDialogEvent.call()
    }

    override fun onSubtaskDialogText(text: String) {
        if (text.isNotEmpty()) {
            subtaskList.add(text)
            subtaskListLiveData.value = subtaskList
        }
    }

    private fun isAllCorrect(): Boolean {
        return nameErrorLiveData.value == null
    }

    private fun validateName(name: String) {
        nameErrorLiveData.value = when {
            name.isEmpty() -> applicationContext.getString(R.string.task_name_error)
            else -> null
        }
    }

    private fun save(name: String, description: String) {
        val task = Task(name, description)
        val subtasks = subtaskList.map { Task(it) }
        val param = CreateTaskUseCase.Param(task, subtasks)
        createTaskUseCase.invoke(viewModelScope, param) { it.fold(::onSavingSuccess, ::onError) }
        finish()
    }

    private fun onSavingSuccess(unit: Unit) {
        finish()
    }

    private fun finish() {
        finishLiveData.value = Unit
    }

    private fun onError(e: Throwable) {
        errorLiveData.value = R.string.error_save_task
    }
}