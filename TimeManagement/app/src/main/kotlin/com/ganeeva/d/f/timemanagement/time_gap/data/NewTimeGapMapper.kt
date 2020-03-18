package com.ganeeva.d.f.timemanagement.time_gap.data

import com.ganeeva.d.f.timemanagement.db.time_gap.DbTimeGap

class NewTimeGapMapper {

    fun map(taskId: Long, startTime: Long) = DbTimeGap(startTime = startTime, taskId = taskId)
}