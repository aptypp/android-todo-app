package com.artur.todoapp.presentation.ui.taskslist.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun FloatingButton(addRow: () -> Unit) {
    FloatingActionButton(onClick = { addRow() }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = ""
        )
    }
}
