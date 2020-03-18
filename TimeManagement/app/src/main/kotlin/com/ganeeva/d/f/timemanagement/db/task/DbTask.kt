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
        parentColumns = ["id"],
        childColumns = ["parentTaskId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE)
    ],
    indices = [
        Index(value = ["parentTaskId"]),
        Index(value = ["creationDate"])
    ]
)

data class DbTask(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val name: String = "",

    val description: String = "",

    val creationDate: Long = 0L,

    val parentTaskId: Long? = null)
{
}