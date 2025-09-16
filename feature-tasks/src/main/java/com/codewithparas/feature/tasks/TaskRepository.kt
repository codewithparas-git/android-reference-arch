package com.codewithparas.feature.tasks

import com.codewithparas.data.db.TaskDao
import com.codewithparas.data.db.TaskEntity
import com.codewithparas.data.db.toTask
import com.codewithparas.feature.tasks.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepository(private val dao: TaskDao) {

    fun getTasks(): Flow<List<Task>> = dao.getAllTasks().map { entities -> entities.map { it.toTask() } }

    suspend fun addTask(title: String) {
        dao.insert(TaskEntity(title = title))
    }

    suspend fun updateTask(task: TaskEntity) = dao.update(task)

    suspend fun deleteTask(task: TaskEntity) = dao.delete(task)

    fun getTaskById(taskId: Int): Flow<Task?> = dao.getTaskById(taskId).map { entity -> entity?.toTask() }
}
