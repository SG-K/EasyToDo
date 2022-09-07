package com.sample.easytodo.feature_todo.presentation.util

sealed class Screen(val route : String) {

    object TasksScreen: Screen("tasks_screen")
    object AddUpdateTaskScreen: Screen("add_edit_task_screen")

}