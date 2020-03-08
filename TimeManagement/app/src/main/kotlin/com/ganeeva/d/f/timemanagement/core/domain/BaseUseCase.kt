package com.ganeeva.d.f.timemanagement.core.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseUseCase<in Param, out Result>() {

    operator fun invoke(
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher,
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