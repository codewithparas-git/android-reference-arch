package com.codewithparas.feature.tasks.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.codewithparas.data.network.TaskApi
import java.util.concurrent.TimeUnit

fun scheduleTaskSync(context: Context) {
    val syncRequest = PeriodicWorkRequestBuilder<TaskSyncWorker>(15, TimeUnit.MINUTES)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "task_sync",
        ExistingPeriodicWorkPolicy.KEEP,
        syncRequest
    )
}
