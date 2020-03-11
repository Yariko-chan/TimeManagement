package com.ganeeva.d.f.timemanagement.new_task.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganeeva.d.f.timemanagement.R
import com.ganeeva.d.f.timemanagement.new_task.domain.CreateTaskUseCase
import com.ganeeva.d.f.timemanagement.task.domain.Task

abstract class NewTaskViewModel: ViewModel() {

    abstract val finishLiveData: LiveData<Unit>
    abstract val errorLiveData: LiveData<Int>

    abstract fun onBackCLicked()
    abstract fun onOkClicked(name: String, description: String)
    abstract fun finish()
}

class DefaultNewTaskViewModel(
    private val createTaskUseCase: CreateTaskUseCase
): NewTaskViewModel() {

    override val finishLiveData = MutableLiveData<Unit>()
    override val errorLiveData = MutableLiveData<Int>()

    override fun onBackCLicked() {
        finish()
    }

    override fun onOkClicked(name: String, description: String) {
        createTaskUseCase.invoke(viewModelScope, Task(name, description)) { it.fold(::onSavingSuccess, ::onError) }
        finish()
    }

    override fun finish() {
        finishLiveData.value = Unit
    }

    private fun onSavingSuccess(unit: Unit) {
        finish()
    }

    private fun onError(e: Throwable) {
        errorLiveData.value = R.string.error_save_task
    }
}