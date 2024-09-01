package com.artur.todoapp

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(TodoState(0, "Tasks"));
    val state get() = _state.asStateFlow()

    fun addRow() {
        _state.update { current ->
            current.copy(taskCount = current.taskCount + 1, topBarLabel = "Tasks Count ${current.taskCount}")
        }
    }
}