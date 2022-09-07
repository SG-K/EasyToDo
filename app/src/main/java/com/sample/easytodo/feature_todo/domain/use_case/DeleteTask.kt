package com.sample.easytodo.feature_todo.domain.use_case

import com.sample.easytodo.feature_todo.domain.model.Task
import com.sample.easytodo.feature_todo.domain.repository.EasyToDoRepository

class DeleteTask (
    private val repository: EasyToDoRepository
) {

    suspend operator fun invoke(task: Task) {
        repository.deleteTask(task)
    }
}