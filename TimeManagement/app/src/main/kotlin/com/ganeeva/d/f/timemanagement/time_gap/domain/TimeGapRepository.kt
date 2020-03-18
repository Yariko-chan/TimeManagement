package com.ganeeva.d.f.timemanagement.time_gap.domain

interface TimeGapRepository {
    fun createTimeGap(taskId: Long, startTime: Long)
    fun updateTimeGap(timeGap: TimeGap)
    fun getAllForTask(taskId: Long) : List<TimeGap>
    fun getUnfinishedTimeGapsForTask(taskId: Long) : List<TimeGap>
}