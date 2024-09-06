package com.artur.todoapp.presentation.ui.taskslist.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(label: String, navigateUp: (() -> Unit)? = null) {
    CenterAlignedTopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        title = {
            Text(label)
        },
        actions = {},
        navigationIcon = {
            if (navigateUp == null) return@CenterAlignedTopAppBar

            IconButton(onClick = { navigateUp() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        })
}
