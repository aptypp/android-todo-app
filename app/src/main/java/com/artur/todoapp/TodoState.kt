package com.artur.todoapp

data class TodoState(
    val tasks: List<TaskData>,
    val topBarLabel: String
)