package com.codewithparas.feature.tasks.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithparas.feature.tasks.repo.TaskRepository
import com.codewithparas.core.common.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskListViewModel(private val repo: TaskRepository) : ViewModel() {

    // Collect tasks from DB
    val uiState: StateFlow<TaskListUiState> =
        repo.getTasks()
            .map { TaskListUiState(tasks = it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskListUiState())

    fun getTask(taskId: Int): StateFlow<Task?> =
        repo.getTaskById(taskId).stateIn(viewModelScope, SharingStarted.Lazily, null)

}

data class TaskListUiState(
    val tasks: List<Task> = emptyList()
)

