package com.artur.todoapp.presentation.ui.taskoverview

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.artur.todoapp.presentation.ui.taskslist.components.TopBar


@Composable
fun TaskOverviewScreen(
    navController: NavHostController,
    id: Long,
    name: String,
    description: String,
    changeTaskData: (Long, String, String) -> Unit
) {
    val averagePadding = 12.dp
    val averageRound = 12.dp

    var inputName by remember { mutableStateOf(name) }
    var inputDescription by remember { mutableStateOf(description) }

    val isDataChanged = inputName != name || inputDescription != description

    val fabScale by animateFloatAsState(
        if (isDataChanged) 1.0f else 0.0f, label = ""
    )

    val textFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    Scaffold(
        topBar = { TopBar("Overview") },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        floatingActionButton = {
            Box(modifier = Modifier.imePadding()) {
                FloatingActionButton(
                    onClick = {
                        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) return@FloatingActionButton

                        changeTaskData(id, inputName, inputDescription)
                        navController.navigateUp()
                    }, modifier = Modifier.scale(fabScale)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = ""
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding).fillMaxSize()
        ) {
            Surface(
                shape = RoundedCornerShape(averageRound),
                modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(
                        start = averagePadding, end = averagePadding
                    )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top
                ) {
                    Row {
                        TextField(
                            value = inputName,
                            onValueChange = { inputName = it },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().padding(averagePadding),
                            shape = RoundedCornerShape(
                                topStart = averageRound, topEnd = averageRound
                            ),
                            colors = textFieldColors,
                            label = {
                                Text("Name")
                            },
                        )
                    }
                    Divider(
                        modifier = Modifier.fillMaxWidth().padding(
                                start = averagePadding, end = averagePadding
                            )
                    )
                    Row {
                        TextField(
                            value = inputDescription,
                            onValueChange = { inputDescription = it },
                            modifier = Modifier.fillMaxWidth().padding(averagePadding),
                            shape = RoundedCornerShape(
                                bottomStart = averageRound, bottomEnd = averageRound
                            ),
                            colors = textFieldColors,
                            label = {
                                Text("Description")
                            },
                        )
                    }
                }
            }
        }
    }
}
