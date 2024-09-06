package com.artur.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.artur.todoapp.presentation.navigation.CreateTaskRoute
import com.artur.todoapp.presentation.navigation.TaskOverviewRoute
import com.artur.todoapp.presentation.navigation.TasksListRoute
import com.artur.todoapp.presentation.theme.TodoAppTheme
import com.artur.todoapp.presentation.ui.createtask.CreateTaskScreen
import com.artur.todoapp.presentation.ui.taskoverview.TaskOverviewScreen
import com.artur.todoapp.presentation.ui.taskslist.TasksListScreen
import com.artur.todoapp.presentation.ui.taskslist.TasksListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: TasksListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val animationDuration = 500

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
                    composable<CreateTaskRoute>(enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left, tween(animationDuration)
                        )
                    }, exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right, tween(animationDuration)
                        )
                    }) {
                        CreateTaskScreen(navController = navController, addTask = { name, desc ->
                            viewModel.addTask(
                                name = name, description = desc
                            )
                        })
                    }
                    composable<TaskOverviewRoute>(enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right, tween(animationDuration)
                        )
                    }, exitTransition = {
                        slideOutOfContainer(
                            AnimatedContentTransitionScope.SlideDirection.Left, tween(animationDuration)
                        )
                    }) { backStack ->
                        val route = backStack.toRoute<TaskOverviewRoute>()
                        TaskOverviewScreen(navController = navController,
                            id = route.id,
                            name = route.name,
                            description = route.description,
                            changeTaskData = { id, name, description ->
                                viewModel.changeTaskData(id, name, description)
                            })
                    }
                }
            }
        }
    }
}