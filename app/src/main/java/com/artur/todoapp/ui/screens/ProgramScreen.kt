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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.artur.todoapp.TaskData
import com.artur.todoapp.TodoViewModel

@Composable
fun ProgramScreen(viewModel: TodoViewModel) {
    val state by viewModel.state.collectAsState()

    var uniqueIndex = 0

    Scaffold(
        topBar = { TopBar(state.topBarLabel) },
        floatingActionButton = { FloatingButton(addRow = { viewModel.addTask(++uniqueIndex) }) },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        TodoList(
            innerPadding = innerPadding,
            tasks = state.tasks,
            removeTask = { viewModel.removeTask(it) },
            changeTaskIsDone = { task, value -> viewModel.changeTaskIsDone(task, value) }
        )
    }
}

@Composable
fun TodoList(
    innerPadding: PaddingValues,
    tasks: List<TaskData>,
    removeTask: (TaskData) -> Unit,
    changeTaskIsDone: (TaskData, Boolean) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize().padding(innerPadding).verticalScroll(rememberScrollState()),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            for (task in tasks) {
                key(task.id) {
                    Divider(thickness = 1.dp)
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TodoCard(
                            task = task,
                            removeTask = removeTask,
                            changeTaskIsDone = changeTaskIsDone
                        )
                    }
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
    FloatingActionButton(onClick = { addRow() }) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCard(
    task: TaskData,
    removeTask: (TaskData) -> Unit,
    changeTaskIsDone: (TaskData, Boolean) -> Unit
) {
    val state = rememberDismissState(positionalThreshold = { it * 0.075f })

    val isDismissed = state.currentValue == DismissValue.DismissedToEnd

    val alpha by animateFloatAsState(
        targetValue = if (isDismissed) 0.0f else 1.0f,
        label = "",
        finishedListener = { removeTask(task) })

    SwipeToDismiss(modifier = Modifier.alpha(alpha), state = state, background = {
        Surface(color = MaterialTheme.colorScheme.errorContainer, modifier = Modifier.fillMaxSize()) {
            Box {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.CenterStart).padding(start = 8.dp)
                )
            }
        }
    }, dismissContent = {
        var isDismissing by remember { mutableStateOf(false) }

        isDismissing = state.dismissDirection == DismissDirection.StartToEnd

        val roundedRadius: Float by animateFloatAsState(if (isDismissing) 16.0f else 0.0f, label = "")

        Surface(
            shape = RoundedCornerShape(topStart = roundedRadius.dp, bottomStart = roundedRadius.dp),
            modifier = Modifier.fillMaxSize().height(75.dp),
        ) {
            Row {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Checkbox(
                        checked = task.isDone,
                        onCheckedChange = { changeTaskIsDone(task, it) },
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = task.name, textDecoration = if (task.isDone) TextDecoration.LineThrough else null
                    )
                }

            }
        }
    }, directions = setOf(DismissDirection.StartToEnd)
    )
}