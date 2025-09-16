// data-db/src/main/java/com/codewithparas/data/db/TaskMappers.kt

package com.codewithparas.data.db

import com.codewithparas.core.common.model.Task


fun TaskEntity.toTask(): Task =
    Task(
        id = id,
        title = title
    )

fun Task.toEntity(): TaskEntity = TaskEntity(
    id = id,
    title = title
)
