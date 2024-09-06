package com.artur.todoapp.presentation.ui.taskslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artur.todoapp.domain.model.TaskData
import com.artur.todoapp.filter.TaskDao
import com.artur.todoapp.filter.TaskEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TasksListViewModel @Inject constructor(
    private val taskDao: TaskDao
) : ViewModel() {
    private val _state = MutableStateFlow(
        TasksListState(
            tasks = listOf(), topBarLabel = "Tasks"
        )
    )
    val state get() = _state.asStateFlow()

    init {
        loadTasks()
    }

    fun addTask(name: String, description: String) {
        val taskEntity = TaskEntity(name = name, description = description)

        viewModelScope.launch(Dispatchers.IO) {
            val id = taskDao.insert(taskEntity)

            withContext(Dispatchers.Main) {
                _state.update {
                    val newTasks = it.tasks.toMutableList().apply {
                        add(TaskData(id = id, name = name, description = description))
                    }

                    it.copy(tasks = newTasks)
                }
            }
        }

    }

    fun removeTask(task: TaskData) {
        val entity = TaskEntity(id = task.id, name = task.name, description = task.description, isDone = task.isDone)

        viewModelScope.launch(Dispatchers.IO) {
            taskDao.delete(entity)
        }

        _state.update {
            val newTasks = it.tasks.toMutableList().apply {
                remove(task)
            }

            it.copy(tasks = newTasks)
        }
    }

    fun changeTaskIsDone(
        task: TaskData, isDone: Boolean
    ) {
        val entity = TaskEntity(id = task.id, name = task.name, description = task.description, isDone = isDone)

        viewModelScope.launch {
            taskDao.update(entity)
        }

        _state.update {
            val newList = it.tasks.toMutableList().apply {
                val index = indexOf(task)

                set(
                    index, task.copy(isDone = isDone)
                )
            }

            it.copy(tasks = newList)
        }
    }

    fun changeTaskData(id: Long, name: String, description: String) {
        val taskData = _state.value.tasks.find { it.id == id }

        if (taskData == null) return

        val entity = TaskEntity(id = id, name = name, description = description, isDone = taskData.isDone)

        viewModelScope.launch {
            taskDao.update(entity)
        }

        _state.update {
            val newList = it.tasks.toMutableList().apply {
                val index = indexOf(taskData)
                set(index, taskData.copy(name = name, description = description))
            }

            it.copy(tasks = newList)
        }
    }

    private fun loadTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = mutableListOf<TaskData>()

            taskDao.getAllTasks().forEach {
                tasks.add(
                    TaskData(
                        id = it.id, name = it.name, description = it.description, isDone = it.isDone
                    )
                )
            }

            withContext(Dispatchers.Main) {
                _state.update {
                    it.copy(tasks = tasks)
                }
            }
        }
    }
}