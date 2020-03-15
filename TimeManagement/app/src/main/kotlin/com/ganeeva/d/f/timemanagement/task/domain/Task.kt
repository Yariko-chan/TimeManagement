package com.ganeeva.d.f.timemanagement.task.domain

data class Task(
    val id: Long,
    val name: String = "",
    val description: String = "",
    val creationDate: Long = System.currentTimeMillis(),
    val subtasks: List<SubTask> = emptyList()
)