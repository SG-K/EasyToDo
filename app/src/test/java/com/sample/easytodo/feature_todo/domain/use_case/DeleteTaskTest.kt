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

class DeleteTaskTest{

    private lateinit var getTasks: GetTasks
    private lateinit var deleteTask: DeleteTask
    private lateinit var fakeRepository: EasyToDoRepository

    @Before
    fun setUp(){

        fakeRepository = FakeEasyToDoRepository()
        getTasks = GetTasks(repository = fakeRepository)
        deleteTask = DeleteTask(repository = fakeRepository)

        val tasksToInsert = mutableListOf<Task>()

        ('a' .. 'd').forEachIndexed { index, data ->
            tasksToInsert.add(
                Task(
                    title = "$data",
                    description = "$data",
                    timeStamp = System.currentTimeMillis(),
                    status = if (index == 0) TASK_STATUS.IN_PROGRESS else if (index == 1) TASK_STATUS.DONE else TASK_STATUS.PENDING,
                    id = index
                )
            )
        }

        runBlocking {
            tasksToInsert.forEach {
                fakeRepository.insetTask(it)
            }
        }

    }

    @Test
    fun `Delete one task`(){
        runBlocking {
            Truth.assertThat(getTasks.invoke(TASK_STATUS.NONE).first().size).isEqualTo(4)
            val needToDeleteTask = fakeRepository.getTaskById(1)
            Truth.assertThat(needToDeleteTask).isNotNull()
            deleteTask.invoke(needToDeleteTask!!)
            Truth.assertThat(getTasks.invoke(TASK_STATUS.NONE).first().size).isEqualTo(3)
        }
    }

}