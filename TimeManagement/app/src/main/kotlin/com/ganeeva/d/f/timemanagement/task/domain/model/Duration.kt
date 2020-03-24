package com.ganeeva.d.f.timemanagement.task.domain.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.ganeeva.d.f.timemanagement.core.*
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class Duration(private val timeGaps: List<TimeGap>): LiveData<Long>() {

    private val durationFormat = SimpleDateFormat(DURATION_FORNAT, Locale.ROOT)
    private val startTimeFormat = SimpleDateFormat(TASK_START_TIME_SEC_FORMAT, Locale.ROOT)

    private val isTaskRunning: Boolean = timeGaps.isNotEmpty() && timeGaps.last().endTime == null
    private val stableSum: Long
    private var incrementJob: Job? = null

    init {
        stableSum = getSavedDuration(timeGaps)
    }

    override fun onActive() {
        super.onActive()
        postValue(stableSum)
        if (isTaskRunning) {
            launchUpdateDuration()
        }
    }

    override fun onInactive() {
        super.onInactive()
        incrementJob?.cancel()
    }

    private fun launchUpdateDuration() {
        incrementJob = Job()
        CoroutineScope(Dispatchers.Default + incrementJob!!).launch {
            while(true) {
                postValue(getCurrentDuration())
                delay(1000)
            }
        }
    }

    private fun getSavedDuration(timeGaps: List<TimeGap>): Long {
        var savedDuration: Long = 0
        timeGaps.forEach {
            if (it.endTime != null) {
                savedDuration += (it.endTime!! - it.startTime)
            }
        }
        return savedDuration
    }

    private fun getCurrentDuration(): Long {
        return if (isTaskRunning) {
            val l = stableSum + (System.currentTimeMillis() - timeGaps.last().startTime)
            Log.d("Duration", "getCurrentDuration ${durationFormat.format(l)} " +
                    "startTime = ${startTimeFormat.format(timeGaps.last().startTime)} " +
                    "System.currentTimeMillis() = ${startTimeFormat.format(System.currentTimeMillis())}")
            l
        } else {
            stableSum
        }
    }
}