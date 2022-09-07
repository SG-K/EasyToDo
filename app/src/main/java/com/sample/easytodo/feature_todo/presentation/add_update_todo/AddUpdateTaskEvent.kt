package com.sample.easytodo.feature_todo.presentation.add_update_todo

import com.sample.easytodo.feature_todo.domain.model.TASK_STATUS

sealed class AddUpdateTaskEvent(){
    data class EnteredTitle(val value: String): AddUpdateTaskEvent()
    data class EnteredDescription(val value: String): AddUpdateTaskEvent()
    data class UpdatedTaskStatus(val value: TASK_STATUS): AddUpdateTaskEvent()
    object saveTask : AddUpdateTaskEvent()
    object deleteTask : AddUpdateTaskEvent()
}
