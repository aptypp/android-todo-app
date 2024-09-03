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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.artur.todoapp.presentation.ui.taskslist.components.TopBar

@Composable
@Preview(showSystemUi = false)
private fun Preview() {
    TaskOverviewScreen(null, "Order a pizza", "Pepperoni with cheese borders\nMargaritta with extra corn")
}

@Composable
fun TaskOverviewScreen(navController: NavHostController?, name: String, description: String) {
    val averagePadding = 12.dp
    val averageRound = 12.dp

    var inputName by remember { mutableStateOf(name) }
    var inputDescription by remember { mutableStateOf(description) }

    val isDataChanged = inputName != name || inputDescription != description

    val fabScale by animateFloatAsState(if (isDataChanged) 1.0f else 0.0f, label = "")

    val textFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )

    Scaffold(
        topBar = { TopBar("Overview") },
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                modifier = Modifier.scale(fabScale)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            Surface(
                shape = RoundedCornerShape(averageRound),
                modifier = Modifier.wrapContentHeight().fillMaxWidth()
                    .padding(start = averagePadding, end = averagePadding)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Row {
                        TextField(
                            value = name,
                            onValueChange = {},
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().padding(averagePadding),
                            shape = RoundedCornerShape(topStart = averageRound, topEnd = averageRound),
                            label = {
                                Text("Name")
                            },
                            colors = textFieldColors
                        )
                    }
                    Divider(
                        modifier = Modifier.fillMaxWidth().padding(start = averagePadding, end = averagePadding)
                    )
                    Row {
                        TextField(
                            value = description,
                            onValueChange = {},
                            modifier = Modifier.fillMaxWidth().padding(averagePadding),
                            shape = RoundedCornerShape(bottomStart = averageRound, bottomEnd = averageRound),
                            label = {
                                Text("Description")
                            },
                            colors = textFieldColors
                        )
                    }
                }
            }
        }
    }
}
