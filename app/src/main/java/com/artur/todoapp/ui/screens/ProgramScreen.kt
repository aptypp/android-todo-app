package com.artur.todoapp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.artur.todoapp.TodoViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun ProgramScreen(viewModel: TodoViewModel, coroutineScope: CoroutineScope) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = { TopBar(state.topBarLabel) },
        floatingActionButton = { FloatingButton(addRow = { viewModel.addRow() }) },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        TodoList(coroutineScope, innerPadding, state.taskCount)
    }
}

@Composable
fun TodoList(coroutineScope: CoroutineScope, innerPadding: PaddingValues, rowsCount: Int) {
    Surface(
        modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(rememberScrollState()),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            for (i in 0..rowsCount) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    TodoCard(coroutineScope)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(label: String) {
    TopAppBar(title = {
        Text(label)
    }, actions = {}, navigationIcon = {})
}

@Composable
fun FloatingButton(addRow: () -> Unit) {
    FloatingActionButton(onClick = { addRow() }, containerColor = MaterialTheme.colorScheme.surface) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCard(coroutineScope: CoroutineScope) {
    var isDismissing by remember { mutableStateOf(false) }

    val roundedRadius: Float by animateFloatAsState(if (isDismissing) 16.0f else 0.0f, label = "")

    val state = rememberDismissState(confirmValueChange = {
        when (it) {
            DismissValue.Default -> return@rememberDismissState false
            DismissValue.DismissedToEnd -> return@rememberDismissState true
            DismissValue.DismissedToStart -> return@rememberDismissState false
        }
    }, positionalThreshold = { it * 0.075f })

    isDismissing = state.dismissDirection == DismissDirection.StartToEnd

    SwipeToDismiss(
        state = state,
        background = {
            Surface(color = MaterialTheme.colorScheme.errorContainer, modifier = Modifier.fillMaxSize()) {
                Box {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        modifier = Modifier.align(Alignment.CenterStart).padding(start = 8.dp)
                    )
                }
            }
        },
        dismissContent = {
            Surface(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(topStart = roundedRadius.dp, bottomStart = roundedRadius.dp),
                modifier = Modifier.fillMaxSize().height(75.dp)
            ) {
                Box {
                    Text(
                        text = "Is Dismissing $isDismissing",
                        modifier = Modifier.padding(4.dp).align(Alignment.CenterStart)
                    )
                }
            }
        },
        directions = setOf(DismissDirection.StartToEnd)
    )
}