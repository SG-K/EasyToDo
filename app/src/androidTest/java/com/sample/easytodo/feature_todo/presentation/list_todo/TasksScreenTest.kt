package com.sample.easytodo.feature_todo.presentation.list_todo

import androidx.compose.runtime.remember
import androidx.compose.ui.test.*
import com.sample.easytodo.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sample.easytodo.core.util.TestTags
import com.sample.easytodo.feature_todo.presentation.MainActivity
import com.sample.easytodo.feature_todo.presentation.add_update_todo.components.AddUpdateTaskScreen
import com.sample.easytodo.feature_todo.presentation.util.Screen
import com.sample.easytodo.ui.theme.EasyToDoTheme
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class TasksScreenTest{

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    @Before
    fun setUp(){
        hiltRule.inject()
//        composeRule.setContent {
//            val navController = rememberNavController()
//            EasyToDoTheme {
//                NavHost(
//                    navController = navController,
//                    startDestination = Screen.AddUpdateTaskScreen.route
//                ){
//                    composable(
//                        route = Screen.AddUpdateTaskScreen.route +
//                                "?taskId={taskId}",
//                        arguments = listOf(
//                            navArgument(
//                                name = "taskId"
//                            ) {
//                                type = NavType.IntType
//                                defaultValue = -1
//                            }
//                        )
//                    ) {
//                        AddUpdateTaskScreen(
//                            navController = navController
//                        )
//                    }
//                }
//            }
//        }

    }

    @Test
    fun clickToggleFilterSection_isVisible(){
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithTag(TestTags.FILTER_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).assertExists()
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).assertIsDisplayed()
    }

    @Test
    fun clickAddTaskFabButton_navigateToAddScreen(){
        composeRule.onNodeWithContentDescription("Add").assertExists()
        composeRule.onNodeWithContentDescription("Add").assertIsDisplayed()
        composeRule.onNodeWithContentDescription("Add").performClick()

        composeRule.onNodeWithContentDescription("Add Task").assertExists()
        composeRule.onNodeWithContentDescription("Add Task").assertIsDisplayed()
    }

    @Test
    fun clickEachTaskStatus_checkSelectedStatus(){

        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithTag(TestTags.FILTER_BUTTON).performClick()
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).assertExists()
        composeRule.onNodeWithTag(TestTags.FILTER_SECTION).assertIsDisplayed()

        composeRule.onNodeWithContentDescription(TestTags.RADIO_NONE).assertIsSelected()

        composeRule.onNodeWithContentDescription(TestTags.RADIO_PENDING).assertExists()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_PENDING).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_PENDING).assertIsNotSelected()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_PENDING).performClick()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_PENDING).assertIsSelected()

        composeRule.onNodeWithContentDescription(TestTags.RADIO_IN_PROGRESS).assertExists()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_IN_PROGRESS).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_IN_PROGRESS).assertIsNotSelected()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_IN_PROGRESS).performClick()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_IN_PROGRESS).assertIsSelected()

        composeRule.onNodeWithContentDescription(TestTags.RADIO_DONE).assertExists()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_DONE).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_DONE).assertIsNotSelected()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_DONE).performClick()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_DONE).assertIsSelected()

        composeRule.onNodeWithContentDescription(TestTags.RADIO_NONE).assertExists()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_NONE).assertIsDisplayed()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_NONE).assertIsNotSelected()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_NONE).performClick()
        composeRule.onNodeWithContentDescription(TestTags.RADIO_NONE).assertIsSelected()

    }

}