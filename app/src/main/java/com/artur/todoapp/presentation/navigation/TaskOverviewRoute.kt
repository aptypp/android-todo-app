package com.artur.todoapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data class TaskOverviewRoute(
    val id: Long, val name: String, val description: String
)