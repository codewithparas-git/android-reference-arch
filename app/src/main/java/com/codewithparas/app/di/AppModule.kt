package com.codewithparas.app.di

import android.content.Context
import androidx.room.Room
import com.codewithparas.data.db.TaskDatabase
import com.codewithparas.feature.tasks.repo.TaskRepository

object AppModule {
    @Volatile private var db: TaskDatabase? = null
    @Volatile private var repo: TaskRepository? = null

    fun provideDatabase(context: Context): TaskDatabase {
        return db ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                "tasks.db"
            ).build().also { db = it }
        }
    }

    fun provideRepository(context: Context): TaskRepository {
        return repo ?: synchronized(this) {
            TaskRepository(provideDatabase(context).taskDao()).also { repo = it }
        }
    }
}
