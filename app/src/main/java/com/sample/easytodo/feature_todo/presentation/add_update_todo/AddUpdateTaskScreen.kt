package com.sample.easytodo.feature_todo.presentation.add_update_todo.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sample.easytodo.feature_todo.domain.model.TASK_STATUS
import com.sample.easytodo.feature_todo.presentation.add_update_todo.AddUpdataToDoViewmodel
import com.sample.easytodo.feature_todo.presentation.add_update_todo.AddUpdateTaskEvent
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddUpdateTaskScreen(
    navController: NavController,
    viewModel: AddUpdataToDoViewmodel = hiltViewModel()
){

    val titleState = viewModel.taskTitle.value
    val descriptionState = viewModel.taskDescription.value
    val statusState = viewModel.taskStatus.value
    val taskIdState = viewModel.taskId.value

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event->
            when(event){
                AddUpdataToDoViewmodel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
                AddUpdataToDoViewmodel.UiEvent.DeleteNote -> {
                    navController.navigateUp()
                }
                is AddUpdataToDoViewmodel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = if (event.message.isEmpty()){
                            "Something went wrong"
                        } else {
                            event.message
                        }
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Text(
                text = if (taskIdState == -1) "Add Task" else "Update Task",
                fontSize = 20.sp,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .padding(16.dp)
                    .semantics { contentDescription = if (taskIdState == -1) "Add Task" else "Update Task" },
                fontWeight = FontWeight(600)
            )
        }
    ){

        Column(
            modifier = Modifier
                .padding( start = 16.dp, end = 16.dp, bottom = 20.dp)
        ) {
            OutlinedTextField(
                value = titleState.text,
                onValueChange = {
                    viewModel.onEvent(AddUpdateTaskEvent.EnteredTitle(it))
                },
                label = { Text(text = "Title") },
                placeholder = { Text(text = titleState.hint) },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = descriptionState.text,
                onValueChange = {
                    viewModel.onEvent(AddUpdateTaskEvent.EnteredDescription(it))
                },
                label = { Text(text = "Description") },
                placeholder = { Text(text = descriptionState.hint) },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            )  {
                RadioButton(
                    selected = statusState == TASK_STATUS.PENDING,
                    onClick = { viewModel.onEvent(AddUpdateTaskEvent.UpdatedTaskStatus(TASK_STATUS.PENDING)) },
                )
                Text(
                    text = "Pending",
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                viewModel.onEvent(
                                    AddUpdateTaskEvent.UpdatedTaskStatus(
                                        TASK_STATUS.PENDING
                                    )
                                )
                            }
                        )
                        .padding(start = 4.dp)
                )

                Spacer(modifier = Modifier.size(4.dp))

                RadioButton(
                    selected = statusState == TASK_STATUS.IN_PROGRESS,
                    onClick = {  viewModel.onEvent(AddUpdateTaskEvent.UpdatedTaskStatus(TASK_STATUS.IN_PROGRESS)) },
                )
                Text(
                    text = "In Progress",
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                viewModel.onEvent(
                                    AddUpdateTaskEvent.UpdatedTaskStatus(
                                        TASK_STATUS.IN_PROGRESS
                                    )
                                )
                            })
                        .padding(start = 4.dp)
                )
                Spacer(modifier = Modifier.size(4.dp))
                RadioButton(
                    selected = statusState == TASK_STATUS.DONE,
                    onClick = {  viewModel.onEvent(AddUpdateTaskEvent.UpdatedTaskStatus(TASK_STATUS.DONE)) },
                )
                Text(
                    text = "Done",
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                viewModel.onEvent(
                                    AddUpdateTaskEvent.UpdatedTaskStatus(
                                        TASK_STATUS.DONE
                                    )
                                )
                            })
                        .padding(start = 4.dp)
                )
            }

            Button(
                onClick = { viewModel.onEvent(AddUpdateTaskEvent.saveTask)},
                modifier = Modifier.semantics { contentDescription = if (taskIdState == -1) "Save" else "Update" }
            ) {

                Text(text = if (taskIdState == -1) "Save" else "Update")

            }

            AnimatedVisibility(taskIdState != -1) {
                Button(
                    onClick = { viewModel.onEvent(AddUpdateTaskEvent.deleteTask)},
                    modifier = Modifier.semantics { contentDescription = "Delete" }
                ) {

                    Text(text = "Delete")

                }
            }

        }

    }

}

@Preview
@Composable
fun PreviewTaskTextField(){
//    AddUpdateTaskScreen(
//
//    )
}