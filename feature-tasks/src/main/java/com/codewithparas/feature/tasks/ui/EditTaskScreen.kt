package com.codewithparas.feature.tasks.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(taskId: String?) {
    if (taskId == null) {
        Text("Create New Task")
    } else {
        Text("Edit Task: $taskId")
    }
}

