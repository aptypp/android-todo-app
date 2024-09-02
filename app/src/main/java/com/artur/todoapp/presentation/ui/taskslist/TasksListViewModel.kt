package com.artur.todoapp.presentation.ui.taskslist

import androidx.lifecycle.ViewModel
import com.artur.todoapp.domain.model.TaskData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(
        TasksListState(
            listOf(),
            "Tasks"
        )
    )
    val state get() = _state.asStateFlow()

    fun addTask(task: TaskData) {
        _state.update {
            val newTasks = it.tasks.toMutableList()
                .apply {
                    add(task)
                }

            it.copy(tasks = newTasks)
        }
    }

    fun removeTask(task: TaskData) {
        _state.update {
            val newTasks = it.tasks.toMutableList()
                .apply {
                    remove(task)
                }

            it.copy(tasks = newTasks)
        }
    }

    fun changeTaskIsDone(
        task: TaskData,
        isDone: Boolean
    ) {
        _state.update {
            val newList = it.tasks.toMutableList()
                .apply {
                    val index = indexOf(task)

                    set(
                        index,
                        task.copy(isDone = isDone)
                    )
                }

            it.copy(tasks = newList)
        }
    }

}