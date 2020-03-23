package com.ganeeva.d.f.timemanagement.tmp.full_task.mapper

import com.ganeeva.d.f.timemanagement.core.Mapper
import com.ganeeva.d.f.timemanagement.db.time_gap.DbTimeGap
import com.ganeeva.d.f.timemanagement.tmp.full_task.domain.model.TimeGap

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