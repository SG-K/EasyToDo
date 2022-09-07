package com.sample.easytodo.feature_todo.presentation.list_todo.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
@Preview
fun TaskListTopBar(
    modifier: Modifier = Modifier,
    showFilter : Boolean = true,
    filterClick : () -> Unit = {}
){


    ConstraintLayout (
        modifier = modifier
            .fillMaxWidth(),
    ){
        val (title, filter) = createRefs()

        Text(
            text = "EasyToDo",
            fontSize = 20.sp,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
                .constrainAs(title) {
                    start.linkTo(parent.start)
                }
                .padding(16.dp),
            fontWeight = FontWeight(600)
        )

        AnimatedVisibility(
            visible = showFilter,
            modifier = Modifier.constrainAs(filter) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            }
        ){
            IconButton(
                onClick = { filterClick() },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Icon(imageVector = Icons.Default.FilterList, contentDescription = "Filter")
            }
        }

    }
}