package com.codewithparas.feature.tasks.ui

import com.codewithparas.feature.tasks.model.Task

data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val currentTask: Task? = null //TODO: Remove from global and keep in edit task screen
)
