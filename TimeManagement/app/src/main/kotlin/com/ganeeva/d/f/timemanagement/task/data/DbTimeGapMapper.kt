package com.ganeeva.d.f.timemanagement.task.data

import com.ganeeva.d.f.timemanagement.core.Mapper
import com.ganeeva.d.f.timemanagement.db.time_gap.DbTimeGap
import com.ganeeva.d.f.timemanagement.task.domain.model.TimeGap

class DbTimeGapMapper: Mapper<DbTimeGap, TimeGap>() {
    override fun map(from: DbTimeGap): TimeGap {
        return TimeGap(
            from.id,
            from.startTime,
            from.endTime,
            from.taskId
        )
    }
}