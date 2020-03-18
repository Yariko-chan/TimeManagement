package com.ganeeva.d.f.timemanagement.task_view.domain.time_gap

import com.ganeeva.d.f.timemanagement.core.domain.ResultUseCase
import com.ganeeva.d.f.timemanagement.core.domain.Either
import com.ganeeva.d.f.timemanagement.core.domain.ResultlessUseCase
import com.ganeeva.d.f.timemanagement.time_gap.domain.TimeGap
import com.ganeeva.d.f.timemanagement.time_gap.domain.TimeGapRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class StopTaskUseCase(
    private val repository: TimeGapRepository
) : ResultlessUseCase<Long>(
    Dispatchers.IO
) {

    override suspend fun run(params: Long): Either<Unit, Throwable> {
        return try {
            val unfinishedGaps = repository.getUnfinishedTimeGapsForTask(params)
            return when {
                unfinishedGaps.size == 0 -> Either.Right(
                    Exception("No unfinished gaps found")
                )
                else -> {
                    unfinishedGaps.forEach { it.finish() }
                    Either.Left(Unit)
                }
            }

        } catch (e: Throwable) {
            Either.Right(e)
        }
    }

    private fun TimeGap.finish() {
        endTime = System.currentTimeMillis()
        repository.updateTimeGap(this)
    }
}