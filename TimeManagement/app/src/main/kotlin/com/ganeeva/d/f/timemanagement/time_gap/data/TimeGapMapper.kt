package com.ganeeva.d.f.timemanagement.time_gap.data

import com.ganeeva.d.f.timemanagement.core.Mapper
import com.ganeeva.d.f.timemanagement.db.time_gap.DbTimeGap
import com.ganeeva.d.f.timemanagement.time_gap.domain.TimeGap

class TimeGapMapper: Mapper<TimeGap, DbTimeGap>() {

    override fun map(from: TimeGap): DbTimeGap {
        return DbTimeGap(from.id, from.startTime, from.endTime, from.taskId)
    }
}