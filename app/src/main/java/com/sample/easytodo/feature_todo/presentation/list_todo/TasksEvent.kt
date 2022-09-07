package com.sample.easytodo.feature_todo.presentation.list_todo

import com.sample.easytodo.feature_todo.domain.model.TASK_STATUS

sealed class TasksEvent{
    data class Filter(val taskStatus: TASK_STATUS):TasksEvent()
    object ToggleFilterSection: TasksEvent()
}
