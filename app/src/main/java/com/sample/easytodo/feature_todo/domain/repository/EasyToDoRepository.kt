package com.sample.easytodo.feature_todo.domain.repository

import com.sample.easytodo.feature_todo.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface EasyToDoRepository {

    fun getTasks(): Flow<List<Task>>

    suspend fun getTaskById(id: Int): Task?

    suspend fun insetTask(task: Task)

    suspend fun deleteTask(task: Task)

}