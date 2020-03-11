package com.ganeeva.d.f.timemanagement.db.task

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    /**
     * @return id for inserted item
     */
    @Insert
    fun insertTask(task: DbTask) : Long

    /**
     * @return count of rows updated
     */
    @Update
    fun updateTask(task: DbTask) : Int

    @Query("SELECT * FROM task_table WHERE taskID = :id")
    fun getById(id: Long): DbTask

    @Query("SELECT * FROM task_table")
    fun getAll(): List<DbTask>
}