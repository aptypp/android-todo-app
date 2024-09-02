package com.artur.todoapp.domain.model

import java.util.*

data class TaskData(
    val id: UUID,
    val name: String,
    val description: String = "",
    val isDone: Boolean = false,
)