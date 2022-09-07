package com.sample.easytodo.feature_todo

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.sample.easytodo.core.util.TestTags
import com.sample.easytodo.di.AppModule
import com.sample.easytodo.feature_todo.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class EasyToDOEndToEndTests {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun SaveNewTask_UpdateAfterwards(){

        // Make sure we've add button on the screen
        composeRule.onNodeWithContentDescription("Add").assertExists()
        composeRule.onNodeWithContentDescription("Add").assertIsDisplayed()
        //Click on the add button to add new task
        composeRule.onNodeWithContentDescription("Add").performClick()

        // Make sure that you navigate to add task screen in add view not update view
        composeRule.onNodeWithContentDescription("Add Task").assertExists()
        composeRule.onNodeWithContentDescription("Add Task").assertIsDisplayed()
        // Add sample title after making sure it's present on the screen
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertExists()
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).performTextInput("Testing title")
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertTextContains("Testing title")
        // Add sample description after making sure it's present on the screen
        composeRule.onNodeWithTag(TestTags.DESP_FIELD).assertExists()
        composeRule.onNodeWithTag(TestTags.DESP_FIELD).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.DESP_FIELD).performTextInput("Testing Description")
        composeRule.onNodeWithTag(TestTags.DESP_FIELD).assertTextContains("Testing Description")
        // Select the status of the task as pending
        composeRule.onNodeWithTag(TestTags.RADIO_PENDING).assertExists()
        composeRule.onNodeWithTag(TestTags.RADIO_PENDING).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.RADIO_PENDING).performClick()
        composeRule.onNodeWithTag(TestTags.RADIO_PENDING).assertIsSelected()
        // Now click on the Save button to add the task to DB
        composeRule.onNodeWithContentDescription("Save").assertExists()
        composeRule.onNodeWithContentDescription("Save").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Save").performClick()

        //Make sure we've come back to the tasks list screen
        composeRule.onNodeWithTag(TestTags.FILTER_BUTTON).assertExists()
        composeRule.onNodeWithTag(TestTags.FILTER_BUTTON).assertIsDisplayed()
        //Check for the task that we add earlier on the list
        composeRule.onNodeWithText("Testing title").assertIsDisplayed()
        //Click on the task to update the title
        composeRule.onNodeWithText("Testing title").performClick()

        //Make sure we landed on update screen
        composeRule.onNodeWithContentDescription("Update Task").assertExists()
        composeRule.onNodeWithContentDescription("Update Task").assertIsDisplayed()
        //Update the title
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertExists()
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).performTextClearance()
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).performTextInput("Updated testing title")
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertTextContains("Updated testing title")
        // Click on the update button to save it in DB
        composeRule.onNodeWithContentDescription("Update").assertExists()
        composeRule.onNodeWithContentDescription("Update").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Update").performClick()

        //Make sure we've come back to the tasks list screen
        composeRule.onNodeWithTag(TestTags.FILTER_BUTTON).assertExists()
        composeRule.onNodeWithTag(TestTags.FILTER_BUTTON).assertIsDisplayed()
        //Check for the task that we add earlier on the list
        composeRule.onNodeWithText("Updated testing title").assertIsDisplayed()

    }

    @Test
    fun AddNewTask_DeleteAfterwards(){
        // Make sure we've add button on the screen
        composeRule.onNodeWithContentDescription("Add").assertExists()
        composeRule.onNodeWithContentDescription("Add").assertIsDisplayed()
        //Click on the add button to add new task
        composeRule.onNodeWithContentDescription("Add").performClick()

        // Make sure that you navigate to add task screen in add view not update view
        composeRule.onNodeWithContentDescription("Add Task").assertExists()
        composeRule.onNodeWithContentDescription("Add Task").assertIsDisplayed()
        // Add sample title after making sure it's present on the screen
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertExists()
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).performTextInput("Testing title")
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertTextContains("Testing title")
        // Add sample description after making sure it's present on the screen
        composeRule.onNodeWithTag(TestTags.DESP_FIELD).assertExists()
        composeRule.onNodeWithTag(TestTags.DESP_FIELD).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.DESP_FIELD).performTextInput("Testing Description")
        composeRule.onNodeWithTag(TestTags.DESP_FIELD).assertTextContains("Testing Description")
        // Select the status of the task as pending
        composeRule.onNodeWithTag(TestTags.RADIO_PENDING).assertExists()
        composeRule.onNodeWithTag(TestTags.RADIO_PENDING).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.RADIO_PENDING).performClick()
        composeRule.onNodeWithTag(TestTags.RADIO_PENDING).assertIsSelected()
        // Now click on the Save button to add the task to DB
        composeRule.onNodeWithContentDescription("Save").assertExists()
        composeRule.onNodeWithContentDescription("Save").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Save").performClick()

        //Make sure we've come back to the tasks list screen
        composeRule.onNodeWithTag(TestTags.FILTER_BUTTON).assertExists()
        composeRule.onNodeWithTag(TestTags.FILTER_BUTTON).assertIsDisplayed()
        //Check for the task that we add earlier on the list
        composeRule.onNodeWithText("Testing title").assertIsDisplayed()
        //Click on the task to delete the task
        composeRule.onNodeWithText("Testing title").performClick()

        //Make sure we landed on update screen
        composeRule.onNodeWithContentDescription("Update Task").assertExists()
        composeRule.onNodeWithContentDescription("Update Task").assertIsDisplayed()
        //Click on the delete button
        composeRule.onNodeWithContentDescription("Delete").assertExists()
        composeRule.onNodeWithContentDescription("Delete").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Delete").performClick()

        // Make sure we've landed on tasks list screen
        composeRule.onNodeWithContentDescription("Add").assertExists()
        composeRule.onNodeWithContentDescription("Add").assertIsDisplayed()
        // Make sure the task is deleted and updated the UI
        composeRule.onNodeWithText("Testing title").assertDoesNotExist()

    }

    @Test
    fun addTasks_checkStatusFilter(){
        // Make sure we've add button on the screen
        composeRule.onNodeWithContentDescription("Add").assertExists()
        composeRule.onNodeWithContentDescription("Add").assertIsDisplayed()

        // Adding tasks in DB based on the alphabets
        ('a'..'i').forEachIndexed { index, c ->
            //Click on the add button to add new task
            composeRule.onNodeWithContentDescription("Add").performClick()

            // Make sure that you navigate to add task screen in add view not update view
            composeRule.onNodeWithContentDescription("Add Task").assertExists()
            composeRule.onNodeWithContentDescription("Add Task").assertIsDisplayed()
            // Add sample title after making sure it's present on the screen
            composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertExists()
            composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertIsDisplayed()
            composeRule.onNodeWithTag(TestTags.TITLE_FIELD).performTextInput("$c")
            composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertTextContains("$c")
            // Add sample description after making sure it's present on the screen
            composeRule.onNodeWithTag(TestTags.DESP_FIELD).assertExists()
            composeRule.onNodeWithTag(TestTags.DESP_FIELD).assertIsDisplayed()
            composeRule.onNodeWithTag(TestTags.DESP_FIELD).performTextInput("$c")
            composeRule.onNodeWithTag(TestTags.DESP_FIELD).assertTextContains("$c")
            // Select the status of the task as per some random logic
            val status = if ( c == 'a' || c == 'd' || c == 'g' ){
                TestTags.RADIO_IN_PROGRESS
            } else if ( c == 'b' || c == 'e' || c == 'h' ){
                TestTags.RADIO_DONE
            } else { // c, f, i
                TestTags.RADIO_PENDING
            }
            composeRule.onNodeWithTag(status).assertExists()
            composeRule.onNodeWithTag(status).assertIsDisplayed()
            composeRule.onNodeWithTag(status).performClick()
            composeRule.onNodeWithTag(status).assertIsSelected()
            // Now click on the Save button to add the task to DB
            composeRule.onNodeWithContentDescription("Save").assertExists()
            composeRule.onNodeWithContentDescription("Save").assertIsDisplayed()
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        //Make sure we've come back to the tasks list screen
        composeRule.onNodeWithTag(TestTags.FILTER_BUTTON).assertExists()
        composeRule.onNodeWithTag(TestTags.FILTER_BUTTON).assertIsDisplayed()
        // Perform filter to display filter section
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithTag(TestTags.FILTER_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).assertExists()
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).assertIsDisplayed()
        // Click on IN PROGRESS status
        composeRule.onNodeWithContentDescription(TestTags.RADIO_IN_PROGRESS).assertExists()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_IN_PROGRESS).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_IN_PROGRESS).performClick()
        //Check all displaed tasks are in progress, as per the data, a,d,g are only pending tasks
        composeRule.onAllNodesWithTag(TestTags.TASK_ITEM).assertCountEquals(4)
        composeRule.onAllNodesWithTag(TestTags.TASK_ITEM)[0].assertTextContains("a")
        composeRule.onAllNodesWithTag(TestTags.TASK_ITEM)[1].assertTextContains("d")
        composeRule.onAllNodesWithTag(TestTags.TASK_ITEM)[2].assertTextContains("g")
        Thread.sleep(2000)
        // Click on DONE status
        composeRule.onNodeWithContentDescription(TestTags.RADIO_DONE).assertExists()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_DONE).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_DONE).performClick()
        //Check all displaed tasks are in progress, as per the data, b, e, h are only pending tasks
        composeRule.onAllNodesWithTag(TestTags.TASK_ITEM).assertCountEquals(4)
        composeRule.onAllNodesWithTag(TestTags.TASK_ITEM)[0].assertTextContains("b")
        composeRule.onAllNodesWithTag(TestTags.TASK_ITEM)[1].assertTextContains("e")
        composeRule.onAllNodesWithTag(TestTags.TASK_ITEM)[2].assertTextContains("h")
        Thread.sleep(2000)
        //Similarly we can write the tests for pending and all filters as well.


    }




}