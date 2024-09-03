package com.artur.todoapp.domain.model

import com.artur.todoapp.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class TaskData(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val description: String = "",
    val isDone: Boolean = false,
)