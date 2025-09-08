package com.codewithparas.feature.tasks.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun TaskNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "task_list"
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("task_list") {
            TaskListScreen(
                onAddTask = {
                    navController.navigate("edit")
                },
                onTaskClick = { taskId ->
                    navController.navigate("edit/$taskId")
                }
            )
        }
        composable("edit") {
            EditTaskScreen(taskId = null) // New task
        }
        composable("edit/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            EditTaskScreen(taskId = taskId)
        }
    }
}
