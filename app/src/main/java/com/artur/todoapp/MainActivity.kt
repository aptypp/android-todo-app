package com.artur.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.artur.todoapp.domain.model.TaskData
import com.artur.todoapp.presentation.navigation.CreateTaskRoute
import com.artur.todoapp.presentation.navigation.TaskOverviewRoute
import com.artur.todoapp.presentation.navigation.TasksListRoute
import com.artur.todoapp.presentation.theme.TodoAppTheme
import com.artur.todoapp.presentation.ui.createtask.CreateTaskScreen
import com.artur.todoapp.presentation.ui.taskoverview.TaskOverviewScreen
import com.artur.todoapp.presentation.ui.taskslist.TasksListScreen
import com.artur.todoapp.presentation.ui.taskslist.TasksListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: TasksListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            TodoAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = TasksListRoute,
                ) {
                    composable<TasksListRoute> {
                        TasksListScreen(
                            viewModel = viewModel, navController = navController
                        )
                    }
                    composable<CreateTaskRoute> {
                        CreateTaskScreen(navController = navController, addTask = { name, desc ->
                            viewModel.addTask(
                                TaskData(
                                    name = name, description = desc, id = Random.nextLong()
                                )
                            )
                        })
                    }
                    composable<TaskOverviewRoute> { backStack ->
                        val route = backStack.toRoute<TaskOverviewRoute>()
                        TaskOverviewScreen(
                            navController = navController,
                            id = route.id,
                            name = route.name,
                            description = route.description,
                            changeTaskData = { id, name, description ->
                                viewModel.changeTaskData(id, name, description)
                            }
                        )
                    }
                }
            }
        }
    }
}