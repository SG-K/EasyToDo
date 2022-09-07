package com.sample.easytodo.feature_todo.presentation.list_todo

import com.sample.easytodo.feature_todo.domain.model.TASK_STATUS
import com.sample.easytodo.feature_todo.domain.model.Task

data class TasksState(
    val tasks: List<Task> = emptyList(),
    val filterType : TASK_STATUS = TASK_STATUS.NONE,
    val isFilterSectionVisible: Boolean = false
)
