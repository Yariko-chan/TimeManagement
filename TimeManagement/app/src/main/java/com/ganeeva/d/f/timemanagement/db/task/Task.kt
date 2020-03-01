package com.ganeeva.d.f.timemanagement.db.task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,

    var name: String = "",

    var description: String = "",

//    @ForeignKey(entity = Task::class)
    var parentTaskID: Long? = null)
{
}