package com.ganeeva.d.f.timemanagement.db.time_gap

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TimeGapDao {

    /**
     * @return id for inserted item
     */
    @Insert
    fun insertTask(task: DbTimeGap) : Long

    /**
     * @return count of rows updated
     */
    @Update
    fun updateTask(task: DbTimeGap) : Int

    @Query("SELECT * FROM time_gap_table WHERE taskId = :taskId")
    fun getAllForTask(taskId: Long): List<DbTimeGap>

    @Query("SELECT * FROM time_gap_table WHERE taskId = :taskId AND endTime IS NULL")
    fun getUnfinishedTimeGapsForTask(taskId: Long): List<DbTimeGap>
}