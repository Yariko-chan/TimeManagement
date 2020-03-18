package com.ganeeva.d.f.timemanagement.time_gap.data

import com.ganeeva.d.f.timemanagement.db.TaskDatabase
import com.ganeeva.d.f.timemanagement.time_gap.domain.TimeGap
import com.ganeeva.d.f.timemanagement.time_gap.domain.TimeGapRepository

class DbTimeGapRepository(
    private val db: TaskDatabase,
    private val newTimeGapMapper: NewTimeGapMapper,
    private val dbTimeGapMapper: DbTimeGapMapper,
    private val timeGapMapper: TimeGapMapper
) : TimeGapRepository {

    override fun createTimeGap(taskId: Long, startTime: Long) {
        db.timeGapDao.insertTask(newTimeGapMapper.map(taskId, startTime))
    }

    override fun updateTimeGap(timeGap: TimeGap) {
        db.timeGapDao.updateTask(timeGapMapper.map(timeGap))
    }

    override fun getAllForTask(taskId: Long): List<TimeGap> {
        return dbTimeGapMapper.map(db.timeGapDao.getAllForTask(taskId))
    }

    override fun getUnfinishedTimeGapsForTask(taskId: Long): List<TimeGap> {
        return dbTimeGapMapper.map(db.timeGapDao.getUnfinishedTimeGapsForTask(taskId))
    }
}