package com.sample.easytodo.feature_todo.presentation.add_update_todo

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.sample.easytodo.core.util.TestTags
import com.sample.easytodo.di.AppModule
import com.sample.easytodo.feature_todo.presentation.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class AddUpdateTaskScreenTest{

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setUp(){
        hiltRule.inject()
        composeRule.onNodeWithContentDescription("Add").assertExists()
        composeRule.onNodeWithContentDescription("Add").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Add").performClick()
    }

    @Test
    fun SaveDeleteButtons_visibility(){
        composeRule.onNodeWithContentDescription("Save").assertExists()
        composeRule.onNodeWithContentDescription("Save").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Delete").assertDoesNotExist()
    }

    @Test
    fun typeTextInTitle_titleVerify(){
        composeRule.onNodeWithContentDescription("Add Task").assertExists()
        composeRule.onNodeWithContentDescription("Add Task").assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertExists()
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).performClick()
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).performTextInput("Testing title")
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertTextContains("Testing title")
    }

    @Test
    fun typeTextInDesp_despVerify(){
        composeRule.onNodeWithContentDescription("Add Task").assertExists()
        composeRule.onNodeWithContentDescription("Add Task").assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.DESP_FIELD).assertExists()
        composeRule.onNodeWithTag(TestTags.DESP_FIELD).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.DESP_FIELD).performClick()
        composeRule.onNodeWithTag(TestTags.DESP_FIELD).performTextInput("Testing Desp")
        composeRule.onNodeWithTag(TestTags.DESP_FIELD).assertTextContains("Testing Desp")
    }

    @Test
    fun clickEachTaskStatus_checkSelectedStatus(){

        composeRule.onNodeWithContentDescription("Add Task").assertExists()
        composeRule.onNodeWithContentDescription("Add Task").assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.RADIO_PENDING).assertIsSelected()

        composeRule.onNodeWithTag(TestTags.RADIO_IN_PROGRESS).assertExists()
        composeRule.onNodeWithTag(TestTags.RADIO_IN_PROGRESS).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.RADIO_IN_PROGRESS).assertIsNotSelected()
        composeRule.onNodeWithTag(TestTags.RADIO_IN_PROGRESS).performClick()
        composeRule.onNodeWithTag(TestTags.RADIO_IN_PROGRESS).assertIsSelected()

        composeRule.onNodeWithTag(TestTags.RADIO_DONE).assertExists()
        composeRule.onNodeWithTag(TestTags.RADIO_DONE).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.RADIO_DONE).assertIsNotSelected()
        composeRule.onNodeWithTag(TestTags.RADIO_DONE).performClick()
        composeRule.onNodeWithTag(TestTags.RADIO_DONE).assertIsSelected()

        composeRule.onNodeWithTag(TestTags.RADIO_PENDING).assertExists()
        composeRule.onNodeWithTag(TestTags.RADIO_PENDING).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.RADIO_PENDING).assertIsNotSelected()
        composeRule.onNodeWithTag(TestTags.RADIO_PENDING).performClick()
        composeRule.onNodeWithTag(TestTags.RADIO_PENDING).assertIsSelected()

    }

    @Test
    fun clickAddButton_exceptionCheck(){
        composeRule.onNodeWithContentDescription("Add Task").assertExists()
        composeRule.onNodeWithContentDescription("Add Task").assertIsDisplayed()

        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertExists()
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).assertIsDisplayed()
        composeRule.onNodeWithTag(TestTags.TITLE_FIELD).performTextClearance()

        composeRule.onNodeWithContentDescription("Save").assertExists()
        composeRule.onNodeWithContentDescription("Save").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Save").performClick()
        composeRule.onNodeWithText("The title of the task can't be empty.").assertExists()
        composeRule.onNodeWithText("The title of the task can't be empty.").assertIsDisplayed()

    }

}