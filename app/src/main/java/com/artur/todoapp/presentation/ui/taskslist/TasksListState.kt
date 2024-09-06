package com.artur.todoapp.presentation.ui.taskslist

import com.artur.todoapp.domain.model.TaskData

data class TasksListState(
    val tasks: List<TaskData>, val topBarLabel: String
)