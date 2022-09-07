package com.sample.easytodo.feature_todo.presentation.list_todo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.easytodo.feature_todo.domain.model.TASK_STATUS
import com.sample.easytodo.feature_todo.domain.use_case.EasyToDoUseCases
import com.sample.easytodo.feature_todo.presentation.util.print
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ToDoListViewmode @Inject constructor(
    private val tasksUseCases: EasyToDoUseCases
) : ViewModel() {

    private val _state = mutableStateOf(TasksState())
    val state: State<TasksState> = _state

    private var getTasksJob: Job? = null

    init {
        getTasks(TASK_STATUS.NONE)
    }

    fun onEvent(tasksEvent: TasksEvent){
        when(tasksEvent){
            is TasksEvent.Filter -> {
                if (tasksEvent.taskStatus != state.value.filterType){
                    getTasks(tasksEvent.taskStatus)
                }
            }
            TasksEvent.ToggleFilterSection -> {
                _state.value = state.value.copy(
                    isFilterSectionVisible = !state.value.isFilterSectionVisible
                )
            }
        }
    }


    private fun getTasks(taskStatus: TASK_STATUS) {
        "Tasks list start".print()
        getTasksJob?.cancel()
        getTasksJob = tasksUseCases.getTasks(taskStatus)
            .onEach { tasks ->
                "Tasks list size : ${tasks.size}".print()
                _state.value = state.value.copy(
                    tasks = tasks,
                    filterType = taskStatus
                )
            }
            .launchIn(viewModelScope)
    }

}