package com.ganeeva.d.f.timemanagement.db.task

import androidx.room.*

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

    @Query("SELECT * FROM task_table WHERE parentTaskID is NULL")
    fun getAllTasks(): List<DbTask>

    @Query("SELECT * FROM task_table WHERE parentTaskID = :id")
    fun getSubTasks(id: Long): List<DbTask>

    @Query("DELETE FROM task_table WHERE taskID = :id")
    fun remove(id: Long) : Int
}