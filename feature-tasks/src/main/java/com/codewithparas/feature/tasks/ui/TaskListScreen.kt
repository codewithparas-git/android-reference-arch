import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.codewithparas.feature.tasks.ui.TaskListUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    state: State<TaskListUiState>,
    snackbarHostState: SnackbarHostState,
    backStackEntry: NavBackStackEntry,
    onAddTask: () -> Unit,
    onTaskClick: (Int) -> Unit
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTask) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text("My Tasks", style = MaterialTheme.typography.headlineMedium)
                },
            )
        }
    ) { padding ->
        Column {
            LazyColumn(contentPadding = padding) {
                items(state.value.tasks) { task ->
                    Text(
                        text = task.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onTaskClick(task.id) }
                            .padding(16.dp)
                    )
                }
            }

            // ✅ Snackbar observer
            ShowSnackbarOnMessage(backStackEntry, snackbarHostState)
//            // Current task (if any)
//            state.currentTask?.let { task ->
//                Text("Selected: ${task.title}", style = MaterialTheme.typography.bodyLarge)
//            }
        }
    }
}
