package com.sample.easytodo.feature_todo.domain.use_case

import com.sample.easytodo.feature_todo.domain.model.InvalidTaskException
import com.sample.easytodo.feature_todo.domain.model.TASK_STATUS
import com.sample.easytodo.feature_todo.domain.model.Task
import com.sample.easytodo.feature_todo.domain.repository.EasyToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTasks (
    private val repository: EasyToDoRepository
) {

    operator fun invoke(status: TASK_STATUS) : Flow<List<Task>>  {
        return repository.getTasks().map {tasks->
            when(status){
                TASK_STATUS.NONE -> {
                    tasks
                }
                else -> tasks.filter { it.status == status }
            }
        }
    }
}