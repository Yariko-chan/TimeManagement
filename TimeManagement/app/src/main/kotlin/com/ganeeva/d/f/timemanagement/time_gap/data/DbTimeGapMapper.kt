package com.ganeeva.d.f.timemanagement.time_gap.data

import com.ganeeva.d.f.timemanagement.core.Mapper
import com.ganeeva.d.f.timemanagement.db.time_gap.DbTimeGap
import com.ganeeva.d.f.timemanagement.time_gap.domain.TimeGap

class DbTimeGapMapper: Mapper<DbTimeGap, TimeGap>() {

    override fun map(from: DbTimeGap): TimeGap {
        return TimeGap(
            id = from.id,
            startTime = from.startTime,
            endTime = from.endTime,
            taskId = from.taskId
        )
    }
}