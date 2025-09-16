package com.codewithparas.feature.tasks.ui

import com.codewithparas.core.common.model.Task

data class TaskUiState(
    val tasks: List<com.codewithparas.core.common.model.Task> = emptyList(),
    val currentTask: com.codewithparas.core.common.model.Task? = null //TODO: Remove from global and keep in edit task screen
)
