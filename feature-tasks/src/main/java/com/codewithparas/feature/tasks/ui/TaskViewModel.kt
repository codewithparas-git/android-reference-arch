package com.codewithparas.feature.tasks.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithparas.data.db.TaskEntity
import com.codewithparas.feature.tasks.TaskRepository
import com.codewithparas.feature.tasks.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel(private val repo: TaskRepository) : ViewModel() {


    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init {
        // Collect tasks from DB
        viewModelScope.launch {
            repo.getTasks().collect { tasks ->
                _uiState.update { it.copy(tasks = tasks) }
            }
        }
    }
    fun getTask(taskId: Int): StateFlow<Task?> =
        repo.getTaskById(taskId).stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun setCurrentTask(taskId: Int?) {
        viewModelScope.launch {
            taskId?.let {
                repo.getTaskById(it)
                    .collect { task ->
                        _uiState.update { it.copy(currentTask = task) }
                    }
            }
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch { repo.updateTask(task) }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch { repo.deleteTask(task) }
    }

    fun addTask(title: String) {
        viewModelScope.launch {
            repo.addTask(title = title)
        }
    }
}
