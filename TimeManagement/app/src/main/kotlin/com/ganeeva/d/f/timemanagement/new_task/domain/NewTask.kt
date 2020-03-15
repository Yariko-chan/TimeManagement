package com.ganeeva.d.f.timemanagement.new_task.domain

data class NewTask (
    val name: String,
    val description: String,
    val subtasks: List<String>?
)