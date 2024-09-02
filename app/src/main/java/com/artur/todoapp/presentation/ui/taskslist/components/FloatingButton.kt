package com.artur.todoapp.presentation.ui.taskslist.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun FloatingButton(addRow: () -> Unit) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    FloatingActionButton(onClick = {
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) return@FloatingActionButton
        addRow()
    }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = ""
        )
    }
}
