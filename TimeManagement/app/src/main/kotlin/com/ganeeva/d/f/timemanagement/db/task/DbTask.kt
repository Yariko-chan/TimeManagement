package com.ganeeva.d.f.timemanagement.db.task

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "task_table",
    foreignKeys = [
        ForeignKey(
        entity = DbTask::class,
        parentColumns = ["taskId"],
        childColumns = ["parentTaskID"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE)
    ],
    indices = [
        Index(value = ["parentTaskID"]),
        Index(value = ["creationDate"])
    ]
)

data class DbTask(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,

    var name: String = "",

    var description: String = "",

    val creationDate: Long = 0L,

    var parentTaskID: Long? = null)
{
}