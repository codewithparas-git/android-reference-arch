package com.codewithparas.feature.tasks.repo

import com.codewithparas.data.db.TaskDao
import com.codewithparas.data.db.TaskEntity
import com.codewithparas.data.db.toTask
import com.codewithparas.core.common.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val dao: TaskDao) {

    fun getTasks(): Flow<List<com.codewithparas.core.common.model.Task>> = dao.getAllTasks().map { entities -> entities.map { it.toTask() } }

    suspend fun addTask(title: String) {
        dao.insert(TaskEntity(title = title))
    }

    suspend fun updateTask(task: TaskEntity) = dao.update(task)

    suspend fun deleteTask(task: TaskEntity) = dao.delete(task)

    fun getTaskById(taskId: Int): Flow<com.codewithparas.core.common.model.Task?> = dao.getTaskById(taskId).map { entity -> entity?.toTask() }
}
