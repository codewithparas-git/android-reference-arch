package com.codewithparas.feature.tasks.repo

import ITaskRepository
import com.codewithparas.data.db.TaskDao
import com.codewithparas.data.db.TaskEntity
import com.codewithparas.data.db.toTask
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class TaskRepository(private val dao: TaskDao) : ITaskRepository {

    override fun getTasks(): Flow<List<com.codewithparas.core.common.model.Task>> = dao.getAllTasks().map { entities -> entities.map { it.toTask() } }

    override suspend fun addTask(title: String) {
        dao.insert(TaskEntity(title = title))
    }

    override suspend fun updateTask(task: TaskEntity) = dao.update(task)

    override suspend fun deleteTask(task: TaskEntity) = dao.delete(task)

    override fun getTaskById(taskId: Int): Flow<com.codewithparas.core.common.model.Task?> = dao.getTaskById(taskId).map { entity -> entity?.toTask() }
}
