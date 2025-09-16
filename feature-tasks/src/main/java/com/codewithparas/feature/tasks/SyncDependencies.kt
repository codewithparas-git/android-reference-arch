package com.codewithparas.feature.tasks.worker

import com.codewithparas.data.network.TaskApi
import com.codewithparas.feature.tasks.repo.TaskRepository

object SyncDependencies {
    lateinit var repository: TaskRepository
    val fakeApi = TaskApi()
}
