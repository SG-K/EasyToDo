package com.sample.easytodo.feature_todo.presentation.list_todo.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sample.easytodo.feature_todo.domain.model.TASK_STATUS
import com.sample.easytodo.feature_todo.domain.model.Task

@Composable
fun TaskItem(
    task: Task,
    modifier: Modifier = Modifier
){

    Card(
        elevation = 5.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(visible = task.description.isNotEmpty()) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

    }

}


@Composable
@Preview
fun PreviewTaskItem(){
    Column {
        for (i in 0..5){
            TaskItem(
                task = Task(
                    title = "SAmple Title $i",
                    description = "Sample Description $i",
                    status = TASK_STATUS.PENDING
                )
            )
        }
    }
}