import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import androidx.compose.material3.SnackbarHostState

@Composable
fun ShowSnackbarOnMessage(
    backStackEntry: NavBackStackEntry,
    snackbarHostState: SnackbarHostState
) {
    val snackbarMessage by backStackEntry.savedStateHandle
        .getStateFlow<String?>("snackbarMessage", null)
        .collectAsState()

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            backStackEntry.savedStateHandle["snackbarMessage"] = null
        }
    }
}
