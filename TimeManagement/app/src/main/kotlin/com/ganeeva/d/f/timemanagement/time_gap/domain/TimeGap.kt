package com.ganeeva.d.f.timemanagement.time_gap.domain


data class TimeGap(
    val id: Long,
    val startTime: Long = 0L,
    var endTime: Long? = null,
    val taskId: Long
)