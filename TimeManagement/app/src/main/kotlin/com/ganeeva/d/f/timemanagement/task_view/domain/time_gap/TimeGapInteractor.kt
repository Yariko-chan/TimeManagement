package com.ganeeva.d.f.timemanagement.task_view.domain.time_gap

import com.ganeeva.d.f.timemanagement.core.domain.Either
import kotlinx.coroutines.CoroutineScope

interface TimeGapInteractor {

    fun startTask(
        scope: CoroutineScope,
        taskId: Long,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    )

    fun stopTask(
        scope: CoroutineScope,
        taskId: Long,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    )
}

class DefaultTimeGapInteractor(
    private val startTaskUseCase: StartTaskUseCase,
    private val stopTaskUseCase: StopTaskUseCase
) : TimeGapInteractor {

    override fun startTask(
        scope: CoroutineScope,
        taskId: Long,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        startTaskUseCase.invoke(scope, taskId, onSuccess, onError)
    }

    override fun stopTask(
        scope: CoroutineScope,
        taskId: Long,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        stopTaskUseCase.invoke(scope, taskId, onSuccess, onError)
    }

}

