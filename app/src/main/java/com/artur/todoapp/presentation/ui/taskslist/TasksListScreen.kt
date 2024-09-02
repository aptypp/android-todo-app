package com.artur.todoapp.presentation.ui.taskslist

import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.artur.todoapp.domain.model.TaskData
import com.artur.todoapp.presentation.ui.taskslist.components.FloatingButton
import com.artur.todoapp.presentation.ui.taskslist.components.TasksList
import com.artur.todoapp.presentation.ui.taskslist.components.TopBar
import java.util.*

@Composable
fun TasksListScreen(viewModel: TasksListViewModel) {
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
        TasksList(innerPadding = innerPadding,
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