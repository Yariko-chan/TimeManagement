package com.ganeeva.d.f.timemanagement.core.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class ResultUseCase<in Param, out Result>(
    private val dispatcher: CoroutineDispatcher) {

    operator fun invoke(
        scope: CoroutineScope,
        param: Param,
        onResult: (Either<Result, Throwable>) -> Unit = {}
    ) {
        scope.launch {
            val run = withContext(dispatcher) {
                run(param)
            }
            onResult(run)
        }
    }

    abstract suspend fun run(params: Param) : Either<Result, Throwable>
}

abstract class ResultlessUseCase<in Param>(
    private val dispatcher: CoroutineDispatcher) {

    operator fun invoke(
        scope: CoroutineScope,
        param: Param,
        onSuccess: () -> Unit = {},
        onError: (Throwable) -> Unit = {}
    ) {
        scope.launch {
            val run = withContext(dispatcher) {
                run(param)
            }
            run.fold({ onSuccess() },{ e -> onError(e) })
        }
    }

    abstract suspend fun run(params: Param) : Either<Unit, Throwable>
}
