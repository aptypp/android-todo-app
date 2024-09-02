package com.artur.todoapp.presentation.ui.taskslist.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(label: String) {
    TopAppBar(title = {
        Text(label)
    },
        actions = {},
        navigationIcon = {})
}
