package com.ganeeva.d.f.timemanagement.tmp.full_task.domain.model

import androidx.lifecycle.LiveData

class SubTask(
    val id: Long,
    val name: String = "",
    val parentID: Long,
    val duration: LiveData<Long>,
    val timeGaps: LiveData<List<TimeGap>>
)