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

    @Query("SELECT * FROM task_table WHERE id = :id")
    fun getById(id: Long): DbTask

    @Query("SELECT * FROM task_table WHERE parentTaskId is NULL AND creationDate > :from AND creationDate < :to")
    fun getAllTasks(from: Long, to: Long): List<DbTask>

    @Query("SELECT * FROM task_table WHERE parentTaskId is NULL AND ((name LIKE '%' || :query  || '%') OR (description LIKE '%' || :query  || '%'))")
    fun searchTasks(query: String): List<DbTask>

    @Query("SELECT * FROM task_table WHERE parentTaskId is NULL AND creationDate > :from AND creationDate < :to ORDER BY CASE WHEN :isAsc = 1 THEN name END ASC, CASE WHEN :isAsc = 0 THEN name END DESC")
    fun getTasksSortAlphabetically(isAsc: Boolean, from: Long, to: Long): List<DbTask>

    @Query("SELECT * FROM task_table WHERE parentTaskId is NULL AND creationDate > :from AND creationDate < :to ORDER BY CASE WHEN :isAsc = 1 THEN creationDate END DESC, CASE WHEN :isAsc = 0 THEN creationDate END ASC")
    fun getTasksSortByCreationDate(isAsc: Boolean, from: Long, to: Long): List<DbTask>

    @Query("SELECT * FROM task_table WHERE parentTaskId = :id")
    fun getSubTasks(id: Long): List<DbTask>

    @Query("DELETE FROM task_table WHERE id = :id")
    fun remove(id: Long) : Int
}