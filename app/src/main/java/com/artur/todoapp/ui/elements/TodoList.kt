package com.artur.todoapp.ui.elements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.artur.todoapp.TaskData

@Composable
fun TodoList(
    innerPadding: PaddingValues,
    tasks: List<TaskData>,
    removeTask: (TaskData) -> Unit,
    changeTaskIsDone: (TaskData, Boolean) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState()),
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
