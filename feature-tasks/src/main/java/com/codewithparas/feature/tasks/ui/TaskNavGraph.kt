package com.codewithparas.feature.tasks.ui

import EditTaskScreen
import TaskListScreen
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun TaskNavGraph(navController: NavHostController, viewModel: TaskViewModel) {
    NavHost(
        navController = navController,
        startDestination = "taskList"
    ) {
        composable("taskList") { backStackEntry ->
            val snackbarHostState = remember { SnackbarHostState() }
            TaskListScreen(
                viewModel,
                snackbarHostState = snackbarHostState,
                backStackEntry = backStackEntry,
                onAddTask = { navController.navigate("editTask") },
                onTaskClick = { taskId -> navController.navigate("editTask/$taskId") }
            )
        }

        composable("editTask") {
            EditTaskScreen(
                viewModel,
                onNavigateBack = { navController.popBackStack() },
                taskId = null,
                onShowMessage = { message ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("snackbarMessage", message)
                }
            )
        }

        composable(
            route = "editTask/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getInt("taskId")
            EditTaskScreen(taskId = taskId, viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onShowMessage = { message ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("snackbarMessage", message)
                })
        }

    }
}



