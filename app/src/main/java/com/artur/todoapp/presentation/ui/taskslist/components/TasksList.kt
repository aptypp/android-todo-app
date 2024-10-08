package com.artur.todoapp.presentation.ui.taskslist.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.artur.todoapp.domain.model.TaskData
import kotlin.random.Random

@Preview
@Composable
private fun Preview() {
    val tasks = listOf(
        TaskData(
            name = "Name1", id = Random.nextLong()
        ), TaskData(
            name = "Name2", id = Random.nextLong()
        ), TaskData(
            name = "Name3", id = Random.nextLong()
        )
    )

    TasksList(tasks, {}, {}, { _, _ -> })
}

@Composable
fun TasksList(
    tasks: List<TaskData>,
    removeTask: (TaskData) -> Unit,
    openTaskViewer: (TaskData) -> Unit,
    changeTaskIsDone: (TaskData, Boolean) -> Unit
) {
    val averageRound = 8.dp
    val averagePadding = 8.dp
    val surfaceRound = 16.dp

    Surface(
        modifier = Modifier.fillMaxWidth().padding(
            start = averagePadding, end = averagePadding
        ), shape = RoundedCornerShape(surfaceRound), color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(averagePadding)
        ) {
            val firstPadding = PaddingValues(0.dp)
            val otherPadding = PaddingValues(top = averagePadding)

            val shapeLonely = RoundedCornerShape(
                averageRound
            )

            val shapeFirst = RoundedCornerShape(
                topStart = averageRound, topEnd = averageRound
            )

            val shapeBetween = RoundedCornerShape(
                topStart = 0.dp, topEnd = 0.dp
            )

            val shapeLast = RoundedCornerShape(
                bottomStart = averageRound, bottomEnd = averageRound
            )

            if (tasks.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(text = "No Tasks Yet", modifier = Modifier.align(Alignment.Center))
                }

                return@Surface
            }

            for (taskIndex in tasks.indices) {
                val task = tasks[taskIndex]

                val padding = if (taskIndex == 0) firstPadding else otherPadding
                val shape = when (taskIndex) {
                    0 -> if (tasks.size == 1) shapeLonely else shapeFirst
                    tasks.indices.last -> shapeLast
                    else -> shapeBetween
                }

                key(task.id) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TaskCard(
                            task = task,
                            removeTask = removeTask,
                            openTaskViewer = openTaskViewer,
                            changeTaskIsDone = changeTaskIsDone,
                            padding = padding,
                            shape = shape
                        )
                    }
                }
            }
        }

    }
}

