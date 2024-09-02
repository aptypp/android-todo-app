package com.artur.todoapp.ui.elements

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.artur.todoapp.TaskData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCard(
    task: TaskData,
    removeTask: (TaskData) -> Unit,
    changeTaskIsDone: (TaskData, Boolean) -> Unit
) {
    val state = rememberDismissState(positionalThreshold = { it * 0.075f })

    val isDismissed = state.currentValue == DismissValue.DismissedToEnd

    val alpha by animateFloatAsState(targetValue = if (isDismissed) 0.0f else 1.0f,
        label = "",
        finishedListener = { removeTask(task) })

    SwipeToDismiss(state = state,
        modifier = Modifier.alpha(alpha),
        directions = setOf(DismissDirection.StartToEnd),
        background = {
            Surface(
                color = MaterialTheme.colorScheme.errorContainer,
                modifier = Modifier.fillMaxSize()
            ) {
                Box {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        modifier = Modifier.align(Alignment.CenterStart)
                            .padding(start = 8.dp)
                    )
                }
            }
        },
        dismissContent = {
            var isDismissing by remember { mutableStateOf(false) }

            isDismissing = state.dismissDirection == DismissDirection.StartToEnd

            val roundedRadius: Float by animateFloatAsState(
                if (isDismissing) 16.0f else 0.0f,
                label = ""
            )

            Surface(
                shape = RoundedCornerShape(
                    topStart = roundedRadius.dp,
                    bottomStart = roundedRadius.dp
                ),
                modifier = Modifier.fillMaxSize()
                    .height(75.dp),
            ) {
                Row {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Checkbox(
                            checked = task.isDone,
                            onCheckedChange = {
                                changeTaskIsDone(
                                    task,
                                    it
                                )
                            },
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = task.name,
                            color = if (task.isDone) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
                            textDecoration = if (task.isDone) TextDecoration.LineThrough else null
                        )
                    }

                }
            }
        })
}