package com.sample.easytodo.feature_todo.domain.use_case

import com.sample.easytodo.feature_todo.domain.model.Task
import com.sample.easytodo.feature_todo.domain.repository.EasyToDoRepository

class GetTask (
    private val repository: EasyToDoRepository
) {

    suspend operator fun invoke(id: Int): Task? {
        return repository.getTaskById(id)
    }
}