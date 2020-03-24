package com.ganeeva.d.f.timemanagement.task.domain.model


data class TimeGap(
    val id: Long,
    val startTime: Long = 0L,
    var endTime: Long? = null,
    val taskId: Long
)