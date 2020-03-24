package com.ganeeva.d.f.timemanagement.task.domain.model.subtask

import androidx.lifecycle.LiveData
import com.ganeeva.d.f.timemanagement.task.domain.model.TimeGap

class SubTask(
    val id: Long,
    val name: String = "",
    val parentID: Long,
    val duration: LiveData<Long>,
    val timeGaps: LiveData<List<TimeGap>>
)