package com.artur.todoapp

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(TodoState(listOf(), "Tasks"));
    val state get() = _state.asStateFlow()

    fun addTask(index: Int) {
        _state.update {
            val newTasks = it.tasks.toMutableList().apply {
                add(TaskData(name = "Task index $index", id = UUID.randomUUID()))
            }

            it.copy(tasks = newTasks)
        }
    }

    fun removeTask(task: TaskData) {
        _state.update {
            val newTasks = it.tasks.toMutableList().apply {
                remove(task)
            }

            it.copy(tasks = newTasks)
        }
    }

    fun changeTaskIsDone(task: TaskData, isDone: Boolean) {
        _state.update {
            val newList = it.tasks.toMutableList().apply {
                val index = indexOf(task)

                set(index, task.copy(isDone = isDone))
            }

            it.copy(tasks = newList)
        }
    }

}