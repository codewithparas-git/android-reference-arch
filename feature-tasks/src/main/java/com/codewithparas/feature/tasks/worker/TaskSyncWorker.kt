package com.codewithparas.feature.tasks.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.codewithparas.data.network.TaskApi
import com.codewithparas.feature.tasks.repo.TaskRepository
import kotlinx.coroutines.flow.first

class TaskSyncWorker(
    context: Context,
    params: WorkerParameters,
    private val repository: TaskRepository,
    private val fakeTaskApi: TaskApi
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
//            val tasks = repository.getTasks().first()
            // TODO: sync with remote server
            // get tasks from local DB
            val tasks = SyncDependencies.repository.getTasks().first()

            // sync with fake server
            SyncDependencies.fakeApi.syncTasks(tasks)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
