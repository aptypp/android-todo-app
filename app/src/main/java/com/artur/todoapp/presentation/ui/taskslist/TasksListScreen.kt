package com.artur.todoapp.presentation.ui.taskslist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.artur.todoapp.presentation.navigation.CreateTaskRoute
import com.artur.todoapp.presentation.navigation.TaskOverviewRoute
import com.artur.todoapp.presentation.ui.taskslist.components.FloatingButton
import com.artur.todoapp.presentation.ui.taskslist.components.TasksList
import com.artur.todoapp.presentation.ui.taskslist.components.TopBar

@Composable
fun TasksListScreen(
    viewModel: TasksListViewModel,
    navController: NavHostController
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = { TopBar(state.topBarLabel) },
        floatingActionButton = {
            FloatingButton(addRow = { navController.navigate(CreateTaskRoute) })
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            TasksList(tasks = state.tasks.reversed(),
                removeTask = { viewModel.removeTask(it) },
                openTaskViewer = {
                    navController.navigate(
                        TaskOverviewRoute(
                            it.id,
                            it.name,
                            it.description
                        )
                    )
                },
                changeTaskIsDone = { task, value ->
                    viewModel.changeTaskIsDone(
                        task,
                        value
                    )
                })
        }
    }
}