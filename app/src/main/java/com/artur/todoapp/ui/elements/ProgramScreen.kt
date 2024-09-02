package com.artur.todoapp.ui.elements

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.artur.todoapp.TaskData
import com.artur.todoapp.TodoViewModel
import java.util.*

@Composable
fun ProgramScreen(viewModel: TodoViewModel) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = { TopBar(state.topBarLabel) },
        floatingActionButton = {
            FloatingButton(addRow = {
                viewModel.addTask(
                    TaskData(
                        name = "asfs",
                        id = UUID.randomUUID()
                    )
                )
            })
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        TodoList(innerPadding = innerPadding,
            tasks = state.tasks,
            removeTask = { viewModel.removeTask(it) },
            changeTaskIsDone = { task, value ->
                viewModel.changeTaskIsDone(
                    task,
                    value
                )
            })
    }
}

@Composable
fun TaskCreatorScreen(innerPadding: PaddingValues) {
    Surface(modifier = Modifier.padding(innerPadding)) {

    }
}