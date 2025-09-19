package com.codewithparas.feature.tasks.ui

import EditTaskScreen
import EditTaskViewModel
import TaskListScreen
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codewithparas.feature.tasks.repo.TaskRepository

@Composable
fun TaskNavGraph(navController: NavHostController, repository: TaskRepository) {
    NavHost(
        navController = navController,
        startDestination = "taskList"
    ) {
        composable("taskList") { backStackEntry ->
            val snackbarHostState = remember { SnackbarHostState() }
            val taskListViewModel: TaskListViewModel = viewModel(
                factory = TaskViewModelFactory { TaskListViewModel(repository) }
            )
            TaskListScreen(
                taskListViewModel.uiState.collectAsState(),
                snackbarHostState = snackbarHostState,
                backStackEntry = backStackEntry,
                onAddTask = { navController.navigate("editTask") },
                onTaskClick = { taskId -> navController.navigate("editTask/$taskId") }
            )
        }

        composable("editTask") {
            val editTaskViewModel : EditTaskViewModel = viewModel(
                factory = TaskViewModelFactory { EditTaskViewModel(repository) }
            )

            val uiState by editTaskViewModel.uiState.collectAsState()
            EditTaskScreen(
                uiState = uiState,
                onNavigateBack = { navController.popBackStack() },
                onSave = { editTaskViewModel.saveTask() },
                onDelete = { editTaskViewModel.deleteTask() },
                onTitleChange = { title -> editTaskViewModel.onTitleChange(title)},
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
            val editTaskViewModel: EditTaskViewModel = viewModel(
                factory = TaskViewModelFactory { EditTaskViewModel(repository) }
            )
            val uiState by editTaskViewModel.uiState.collectAsState()

            // Load once when entering this route
            LaunchedEffect(taskId) {
                if (taskId != null) editTaskViewModel.loadTask(taskId)
            }
            EditTaskScreen(
                uiState = uiState,
                onNavigateBack = { navController.popBackStack() },
                onSave = { editTaskViewModel.saveTask() },
                onDelete = { editTaskViewModel.deleteTask() },
                onTitleChange = { title -> editTaskViewModel.onTitleChange(title)},
                onShowMessage = { message ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("snackbarMessage", message)
                })
        }

    }
}



