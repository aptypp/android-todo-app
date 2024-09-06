package com.artur.todoapp.presentation.ui.createtask

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
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
fun CreateTaskScreen(
    navController: NavHostController, addTask: (String, String) -> Unit
) {
    val averagePadding = 8.dp
    val averageRound = 8.dp
    val surfaceRound = 16.dp

    var inputName by remember { mutableStateOf("") }
    var inputDescription by remember { mutableStateOf("") }

    var isNameNotValid: Boolean by remember { mutableStateOf(true) }

    val fabScale by animateFloatAsState(
        if (isNameNotValid) 0.0f else 1.0f, label = ""
    )

    val textFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val nameData = inputName.trim()
    val descriptionData = inputDescription.trim()

    isNameNotValid = nameData.isEmpty()

    Scaffold(
        topBar = { TopBar(label = "Create task", navigateUp = { navController.navigateUp() }) },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        floatingActionButton = {
            Box(modifier = Modifier.imePadding()) {
                FloatingActionButton(modifier = Modifier.scale(fabScale), onClick = {
                    if (!lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) return@FloatingActionButton
                    if (isNameNotValid) return@FloatingActionButton

                    addTask(
                        nameData,
                        descriptionData,
                    )
                    navController.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Done, contentDescription = ""
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
                shape = RoundedCornerShape(surfaceRound),
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
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = averagePadding, start = averagePadding, end = averagePadding),
                            shape = RoundedCornerShape(
                                topStart = averageRound, topEnd = averageRound
                            ),
                            colors = textFieldColors,
                            label = {
                                Text("Name")
                            },
                        )
                    }
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

/*

isError = isNameNotValid,
keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
keyboardActions = KeyboardActions(onDone = { keyboard?.hide() })

*/