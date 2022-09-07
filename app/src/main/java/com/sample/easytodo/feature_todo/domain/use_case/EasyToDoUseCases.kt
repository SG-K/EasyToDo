package com.sample.easytodo.feature_todo.domain.use_case

data class EasyToDoUseCases(
    val addTask: AddTask,
    val getTasks: GetTasks,
    val getTask: GetTask,
    val deleteTask: DeleteTask
)
