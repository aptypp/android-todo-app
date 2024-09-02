package com.artur.todoapp.presentation.ui.createtask

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import com.artur.todoapp.presentation.ui.taskslist.components.TopBar

@Composable
fun CreateTaskScreen(navController: NavHostController, addTask: (String, String) -> Unit) {
    var isNameNotValid: Boolean by remember { mutableStateOf(false) }
    var nameInput: String by remember { mutableStateOf("") }
    var descriptionInput: String by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopBar("Create task") },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val nameData = nameInput.trim()
                val descriptionData = descriptionInput.trim()

                if (nameData.isEmpty()) {
                    isNameNotValid = true
                    return@FloatingActionButton
                }

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
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            val keyboard = LocalSoftwareKeyboardController.current

            Column {
                Row {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        label = { Text("Name") },
                        singleLine = true,
                        isError = isNameNotValid,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { keyboard?.hide() })
                    )
                }
                Row {
                    TextField(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
                        value = descriptionInput,
                        onValueChange = { descriptionInput = it },
                        label = { Text("Description") },
                    )
                }
            }
        }
    }
}
