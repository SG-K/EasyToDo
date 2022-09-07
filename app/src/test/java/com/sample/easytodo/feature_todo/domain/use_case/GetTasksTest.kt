package com.sample.easytodo.feature_todo.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.sample.easytodo.feature_todo.data.Repository.FakeEasyToDoRepository
import com.sample.easytodo.feature_todo.domain.model.TASK_STATUS
import com.sample.easytodo.feature_todo.domain.model.Task
import com.sample.easytodo.feature_todo.domain.repository.EasyToDoRepository
import com.sample.easytodo.feature_todo.presentation.util.print
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


internal class GetTasksTest{

    private lateinit var getTasks: GetTasks
    private lateinit var fakeRepository: EasyToDoRepository

    @Before
    fun setUp(){

        fakeRepository = FakeEasyToDoRepository()
        getTasks = GetTasks(repository = fakeRepository)

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
                fakeRepository.insetTask(it)
            }
        }

    }

    @Test
    fun `Get all tasks`() = runBlocking{
        val filterType = TASK_STATUS.NONE
        val tasksByStatus = getTasks.invoke(filterType).first()
        assertThat(tasksByStatus.size).isEqualTo(4)
    }

    @Test
    fun `Get pending tasks`() = runBlocking{
        val filterType = TASK_STATUS.PENDING
        val tasksByStatus = getTasks.invoke(filterType).first()
        for(element in tasksByStatus) {
            assertThat(element.status).isEquivalentAccordingToCompareTo(TASK_STATUS.PENDING)
        }
    }

    @Test
    fun `Get In Progress tasks`(): Unit = runBlocking{
        val filterType = TASK_STATUS.IN_PROGRESS
        val tasksByStatus = getTasks.invoke(filterType).first()
        assertThat(tasksByStatus.size).isEqualTo(1)
        for(element in tasksByStatus) {
            assertThat(element.status).isEquivalentAccordingToCompareTo(filterType)
        }
    }

    @Test
    fun `Get Done tasks`(): Unit = runBlocking{
        val filterType = TASK_STATUS.DONE
        val tasksByStatus = getTasks.invoke(filterType).first()
        assertThat(tasksByStatus.size).isEqualTo(1)
        for(element in tasksByStatus) {
            assertThat(element.status).isEquivalentAccordingToCompareTo(filterType)
        }
    }

}