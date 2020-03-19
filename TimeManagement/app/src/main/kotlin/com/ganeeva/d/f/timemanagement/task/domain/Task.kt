package com.ganeeva.d.f.timemanagement.task.domain

/**
 *
 * @param duration sum of all [timeGaps] except unfinished ones
 */
data class Task(
    val id: Long,
    val name: String = "",
    val description: String = "",
    val creationDate: Long = System.currentTimeMillis(),
    val subtasks: List<SubTask> = emptyList(),
    val timeGaps: List<TimeGap> = emptyList(),
    val duration: Long = 0L
)