package com.ganeeva.d.f.timemanagement.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ganeeva.d.f.timemanagement.db.task.DbTask
import com.ganeeva.d.f.timemanagement.db.task.TaskDao


const val DB_NAME = "task_database"

@Database(
    entities = [DbTask::class],
    version = 1,
    exportSchema = false
)

abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao
    companion object {

        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context): TaskDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        DB_NAME)
                        .build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}