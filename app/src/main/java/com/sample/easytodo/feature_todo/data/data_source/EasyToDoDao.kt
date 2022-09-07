package com.sample.easytodo.feature_todo.data.data_source

import androidx.room.*
import com.sample.easytodo.feature_todo.domain.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface EasyToDoDao {
    @Query("SELECT * FROM task")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT * FROM Task WHERE id = :id")
    suspend fun getTaskById(id: Int): Task?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(Task: Task)

    @Delete
    suspend fun deleteTask(Task: Task)
}