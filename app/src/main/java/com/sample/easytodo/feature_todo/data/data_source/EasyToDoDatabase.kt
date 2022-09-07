package com.sample.easytodo.feature_todo.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.easytodo.feature_todo.domain.model.Task

@Database(
    entities = [Task::class],
    version = 1
)
abstract class EasyToDoDatabase : RoomDatabase() {

    abstract val easyToDoDao : EasyToDoDao
    companion object {
        const val DATABASE_NAME = "easy_todo_db"
    }

}