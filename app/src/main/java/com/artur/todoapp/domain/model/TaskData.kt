package com.artur.todoapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TaskData(
    val id: Long = 0,
    val name: String,
    val description: String = "",
    val isDone: Boolean = false,
)