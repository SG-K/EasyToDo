package com.sample.easytodo.feature_todo.data.Repository

import com.sample.easytodo.feature_todo.domain.model.Task
import com.sample.easytodo.feature_todo.domain.repository.EasyToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeEasyToDoRepository : EasyToDoRepository {

    val tasks  = mutableListOf<Task>()

    override fun getTasks(): Flow<List<Task>> {
        return flow { emit(tasks) }
    }

    override suspend fun getTaskById(id: Int): Task? {
        return tasks[id]
    }

    override suspend fun insetTask(task: Task) {
        tasks.add(task)
    }

    override suspend fun deleteTask(task: Task) {
        tasks.remove(task)
    }

}