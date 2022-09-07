package com.sample.easytodo.feature_todo.domain.use_case

import com.sample.easytodo.feature_todo.domain.model.InvalidTaskException
import com.sample.easytodo.feature_todo.domain.model.Task
import com.sample.easytodo.feature_todo.domain.repository.EasyToDoRepository

class AddTask(
    private val repository: EasyToDoRepository
) {

    @Throws(InvalidTaskException::class)
    suspend operator fun invoke(task: Task) {
        if(task.title.isBlank()) {
            throw InvalidTaskException("The title of the task can't be empty.")
        }
        repository.insetTask(task)
    }
}