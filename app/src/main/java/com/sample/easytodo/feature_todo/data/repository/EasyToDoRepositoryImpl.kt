package com.sample.easytodo.feature_todo.data.repository

import com.sample.easytodo.feature_todo.data.data_source.EasyToDoDao
import com.sample.easytodo.feature_todo.domain.model.Task
import com.sample.easytodo.feature_todo.domain.repository.EasyToDoRepository
import kotlinx.coroutines.flow.Flow

class EasyToDoRepositoryImpl(
    private val dao: EasyToDoDao
) : EasyToDoRepository {

    override fun getTasks(): Flow<List<Task>> {
        return dao.getTasks()
    }

    override suspend fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)
    }

    override suspend fun insetTask(task: Task) {
        dao.insertTask(task)
    }

    override suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }

}