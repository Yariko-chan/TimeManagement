package com.ganeeva.d.f.timemanagement.task_running

import com.ganeeva.d.f.timemanagement.core.domain.Either
import com.ganeeva.d.f.timemanagement.core.domain.ResultlessUseCase
import com.ganeeva.d.f.timemanagement.time_gap.domain.TimeGapRepository
import kotlinx.coroutines.Dispatchers

class StartTaskUseCase(
    private val repository: TimeGapRepository
) : ResultlessUseCase<Long>(
    Dispatchers.IO
) {

    override suspend fun run(params: Long): Either<Unit, Throwable> {
        return try {
            repository.createTimeGap(params, System.currentTimeMillis())
            Either.Left(Unit)
        } catch (e: Throwable) {
            Either.Right(e)
        }
    }
}