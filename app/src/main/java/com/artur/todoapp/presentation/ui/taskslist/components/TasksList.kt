package com.artur.todoapp.presentation.ui.taskslist.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.artur.todoapp.domain.model.TaskData

@Composable
fun TasksList(
    tasks: List<TaskData>,
    removeTask: (TaskData) -> Unit,
    openTaskViewer: (TaskData) -> Unit,
    changeTaskIsDone: (TaskData, Boolean) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        for (task in tasks) {
            key(task.id) {
                Divider(thickness = 1.dp)
                Row(modifier = Modifier.fillMaxWidth()) {
                    TaskCard(
                        task = task,
                        removeTask = removeTask,
                        openTaskViewer = openTaskViewer,
                        changeTaskIsDone = changeTaskIsDone
                    )
                }
            }
        }
    }
}

