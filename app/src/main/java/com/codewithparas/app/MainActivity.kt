package com.codewithparas.app

import TaskViewModelFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.codewithparas.app.di.AppModule
import com.codewithparas.core.designsystem.theme.AppTheme
import com.codewithparas.feature.tasks.ui.TaskNavGraph
import com.codewithparas.feature.tasks.ui.TaskViewModel
import com.codewithparas.feature.tasks.worker.SyncDependencies
import com.codewithparas.feature.tasks.worker.scheduleTaskSync

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repo = AppModule.provideRepository(applicationContext)
        enableEdgeToEdge()
        setContent {
            val factory = TaskViewModelFactory(repo)
            val viewModel: TaskViewModel = viewModel(factory = factory)

            AppTheme {
                TaskNavGraph(navController = rememberNavController(), viewModel = viewModel)
            }
        }
        SyncDependencies.repository = repo

        scheduleTaskSync(applicationContext)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Greeting("Android")
    }
}