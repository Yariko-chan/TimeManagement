package com.ganeeva.d.f.timemanagement.db.time_gap

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ganeeva.d.f.timemanagement.db.task.DbTask

@Entity(
    tableName = "time_gap_table",
    foreignKeys = [
        ForeignKey(
            entity = DbTask::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE)
    ],
    indices = [
        Index(value = ["taskId"]),
        Index(value = ["startTime"]),
        Index(value = ["endTime"])
    ]
)

data class DbTimeGap(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val startTime: Long = 0L,

    val endTime: Long? = null,

    val taskId: Long
)