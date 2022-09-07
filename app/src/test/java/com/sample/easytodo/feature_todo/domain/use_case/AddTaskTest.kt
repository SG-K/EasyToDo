package com.sample.easytodo.feature_todo.domain.use_case

import com.google.common.truth.Truth
import com.sample.easytodo.feature_todo.data.Repository.FakeEasyToDoRepository
import com.sample.easytodo.feature_todo.domain.model.TASK_STATUS
import com.sample.easytodo.feature_todo.domain.model.Task
import com.sample.easytodo.feature_todo.domain.repository.EasyToDoRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddTaskTest{

    private lateinit var getTasks: GetTasks
    private lateinit var addTask: AddTask
    private lateinit var fakeRepository: EasyToDoRepository

    @Before
    fun setUp(){

        fakeRepository = FakeEasyToDoRepository()
        getTasks = GetTasks(repository = fakeRepository)
        addTask = AddTask(repository = fakeRepository)

    }

    @Test
    fun `Add Tasks and Get the size`(){
        `Add tasks`()
        `Get all tasks`()
    }

    private fun `Add tasks`(){
        val tasksToInsert = mutableListOf<Task>()

        ('a' .. 'd').forEachIndexed { index, data ->
            tasksToInsert.add(
                Task(
                    title = "$data",
                    description = "$data",
                    timeStamp = System.currentTimeMillis(),
                    status = if (index == 0) TASK_STATUS.IN_PROGRESS else if (index == 1) TASK_STATUS.DONE else TASK_STATUS.PENDING
                )
            )
        }

        runBlocking {
            tasksToInsert.forEach {
                addTask.invoke(it)
            }
        }
    }

    private fun `Get all tasks`() = runBlocking{
        val filterType = TASK_STATUS.NONE
        val tasksByStatus = getTasks.invoke(filterType).first()
        Truth.assertThat(tasksByStatus.size).isEqualTo(4)
    }


}