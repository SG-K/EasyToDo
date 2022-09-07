package com.sample.easytodo.feature_todo.presentation.list_todo

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sample.easytodo.core.util.TestTags
import com.sample.easytodo.feature_todo.domain.model.TASK_STATUS
import com.sample.easytodo.feature_todo.presentation.add_update_todo.AddUpdateTaskEvent
import com.sample.easytodo.feature_todo.presentation.list_todo.components.TaskItem
import com.sample.easytodo.feature_todo.presentation.list_todo.components.TaskListTopBar
import com.sample.easytodo.feature_todo.presentation.util.Screen
import com.sample.easytodo.feature_todo.presentation.util.print

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TasksScreen(
    navController: NavController,
    viewModel: ToDoListViewmode = hiltViewModel()
){

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    Scaffold(
        topBar = {

            TaskListTopBar(
//                showFilter = state.tasks.isNotEmpty()
                showFilter = true
            ){
                viewModel.onEvent(TasksEvent.ToggleFilterSection)
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddUpdateTaskScreen.route)
                },
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier
                    .semantics { contentDescription = "Add" }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Icon")
            }
        },
        scaffoldState = scaffoldState
    ) {

        AnimatedVisibility(visible = state.tasks.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "No tasks to show up",
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
                )
                Text(
                    text = "Add new tasks by clicking on plus button",
                    fontSize = 14.sp,
                    fontWeight = FontWeight(300),
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                )

            }
        }

        Column (){
            AnimatedVisibility(
                visible = state.isFilterSectionVisible,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(TestTags.FILTER_SECTION)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    )  {
                        RadioButton(
                            selected = state.filterType == TASK_STATUS.NONE,
                            onClick = { viewModel.onEvent(
                                TasksEvent.Filter(
                                    TASK_STATUS.NONE)) },
                            modifier = Modifier.semantics { contentDescription = TestTags.RADIO_NONE }
                        )
                        Text(
                            text = "All",
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        viewModel.onEvent(
                                            TasksEvent.Filter(
                                                TASK_STATUS.NONE
                                            )
                                        )
                                    }
                                )
                                .padding(start = 4.dp)
                        )

                        Spacer(modifier = Modifier.size(4.dp))

                        RadioButton(
                            selected = state.filterType == TASK_STATUS.PENDING,
                            onClick = {  viewModel.onEvent(
                                TasksEvent.Filter(
                                    TASK_STATUS.PENDING)) },
                            modifier = Modifier.semantics { contentDescription = TestTags.RADIO_PENDING }
                        )
                        Text(
                            text = "Pending",
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        viewModel.onEvent(
                                            TasksEvent.Filter(
                                                TASK_STATUS.PENDING
                                            )
                                        )
                                    })
                                .padding(start = 4.dp)
                        )

                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    )  {
                        RadioButton(
                            selected = state.filterType == TASK_STATUS.IN_PROGRESS,
                            onClick = { viewModel.onEvent(
                                TasksEvent.Filter(
                                    TASK_STATUS.IN_PROGRESS)) },
                            modifier = Modifier.semantics { contentDescription = TestTags.RADIO_IN_PROGRESS }
                        )
                        Text(
                            text = "In Progress",
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        viewModel.onEvent(
                                            TasksEvent.Filter(
                                                TASK_STATUS.IN_PROGRESS
                                            )
                                        )
                                    }
                                )
                                .padding(start = 4.dp)
                        )

                        Spacer(modifier = Modifier.size(4.dp))

                        RadioButton(
                            selected = state.filterType == TASK_STATUS.DONE,
                            onClick = {  viewModel.onEvent(
                                TasksEvent.Filter(
                                    TASK_STATUS.DONE)) },
                            modifier = Modifier.semantics { contentDescription = TestTags.RADIO_DONE }
                        )
                        Text(
                            text = "Done",
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        viewModel.onEvent(
                                            TasksEvent.Filter(
                                                TASK_STATUS.DONE
                                            )
                                        )
                                    })
                                .padding(start = 4.dp)
                        )

                    }
                }
            }

            AnimatedVisibility(visible = state.tasks.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.tasks) { task ->
                        TaskItem(
                            task = task,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    "Task id in the click of the list - ${task.id}".print()
                                    navController.navigate(
                                        Screen.AddUpdateTaskScreen.route +
                                                "?taskId=${task.id}"
                                    )
                                }
                                .testTag(TestTags.TASK_ITEM)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }




    }



}

















