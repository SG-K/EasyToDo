package com.sample.easytodo.feature_todo.presentation.add_update_todo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.easytodo.feature_todo.domain.model.TASK_STATUS
import com.sample.easytodo.feature_todo.domain.model.Task
import com.sample.easytodo.feature_todo.domain.use_case.EasyToDoUseCases
import com.sample.easytodo.feature_todo.presentation.util.print
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AddUpdataToDoViewmodel @Inject constructor(
    private val easyToDoUseCases: EasyToDoUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _taskTitle = mutableStateOf(TaskTextFieldState(
        hint = "Enter title..."
    ))
    val taskTitle: State<TaskTextFieldState> = _taskTitle

    private val _taskDescription = mutableStateOf(TaskTextFieldState(
        hint = "Enter some description"
    ))
    val taskDescription: State<TaskTextFieldState> = _taskDescription

    private val _taskStatus = mutableStateOf(TASK_STATUS.PENDING)
    val taskStatus: State<TASK_STATUS> = _taskStatus

    private val _taskId = mutableStateOf(-1)
    val taskId: State<Int> = _taskId

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        "Got into viewmode init".print()
        savedStateHandle.get<Int>("taskId")?.let { taskId ->
            "Got into viewmode taskId - $taskId".print()
            if(taskId != -1) {
                viewModelScope.launch {
                    easyToDoUseCases.getTask(taskId)?.also { task ->
                        "Got into viewmode easyToDoUseCases - ${task.id}".print()
                        _taskId.value = taskId
                        _taskTitle.value = taskTitle.value.copy(
                            text = task.title,
                        )
                        _taskDescription.value = taskDescription.value.copy(
                            text = task.description,
                        )
                        _taskStatus.value = task.status
                    }
                }
            }
        }
    }

    private fun deleteTask(){
        if (taskId.value != -1){
            viewModelScope.launch {
                try{
                    easyToDoUseCases.deleteTask(generateTask())
                    _eventFlow.emit(UiEvent.DeleteNote)
                }catch (e:Exception){
                    _eventFlow.emit(UiEvent.ShowSnackbar(message = e.message?:""))
                }
            }
        }
    }

    private fun generateTask() : Task{
        return if (taskId.value != -1){
            Task(
                title = taskTitle.value.text,
                description = taskDescription.value.text,
                timeStamp = System.currentTimeMillis(),
                status = taskStatus.value,
                id = taskId.value
            )
        } else {
            Task(
                title = taskTitle.value.text,
                description = taskDescription.value.text,
                timeStamp = System.currentTimeMillis(),
                status = taskStatus.value,
            )
        }

    }

    private fun addUpdateFunction(){
        viewModelScope.launch {
            try {
                easyToDoUseCases.addTask(generateTask())
                _eventFlow.emit(UiEvent.SaveNote)
            } catch (e:Exception){
                _eventFlow.emit(UiEvent.ShowSnackbar(message = e.message?:""))
            }
        }
    }

    fun onEvent(event: AddUpdateTaskEvent) {
        when(event){
            is AddUpdateTaskEvent.EnteredTitle -> {
                _taskTitle.value = taskTitle.value.copy(
                    text = event.value
                )
            }
            is AddUpdateTaskEvent.EnteredDescription -> {
                _taskDescription.value = taskDescription.value.copy(
                    text = event.value
                )
            }
            is AddUpdateTaskEvent.UpdatedTaskStatus -> {
                _taskStatus.value = event.value
            }

            is AddUpdateTaskEvent.saveTask -> {
                addUpdateFunction()
            }

            is AddUpdateTaskEvent.deleteTask -> {
                deleteTask()
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
        object DeleteNote: UiEvent()
    }

}