package com.artur.todoapp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data class TaskOverviewRoute(val name: String, val description: String)