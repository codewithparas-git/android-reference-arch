package com.codewithparas.data.network

import com.codewithparas.core.common.model.Task
import kotlinx.coroutines.delay

class TaskApi {

    private val remoteTasks = mutableListOf<Task>()

    suspend fun getTasks(): List<Task> {
        delay(1000) // simulate network
        return remoteTasks.toList()
    }

    suspend fun syncTasks(tasks: List<Task>) {
        delay(1000) // simulate network
        remoteTasks.clear()
        remoteTasks.addAll(tasks)
    }
}
