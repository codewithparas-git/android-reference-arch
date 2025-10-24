import app.cash.turbine.test
import com.codewithparas.core.common.model.Task
import com.codewithparas.feature.tasks.ui.TaskListViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class TaskListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeRepo: FakeTaskRepository
    private lateinit var viewModel: TaskListViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepo = FakeTaskRepository()
        viewModel = TaskListViewModel(fakeRepo)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

//    @Test
//    fun `uiState should update when repo emits tasks`() = runTest {
//        val tasks = listOf(Task(1, "Task1"), Task(2, "Task2"))
//
//        // Start collecting uiState
//        val job = launch { viewModel.uiState.collect() }
//
//        fakeRepo.emitTasks(tasks)
//        testDispatcher.scheduler.advanceUntilIdle()
//
//        assertEquals(tasks, viewModel.uiState.value.tasks)
//
//        job.cancel() // cleanup
//    }


    @Test
    fun `uiState should start with empty tasks`() = runTest {
        viewModel.uiState.test {
            val initial = awaitItem()
            assertEquals(emptyList<Task>(), initial.tasks)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `uiState should update when repository emits tasks`() = runTest {
        val task1 = Task(id = 1, title = "Task 1")
        val task2 = Task(id = 2, title = "Task 2")

        viewModel.uiState.test {
            // first emission is the default empty list
            assertEquals(0, awaitItem().tasks.size)

            // now emit new tasks from repo
            fakeRepo.emitTasks(listOf(task1, task2))

            advanceUntilIdle() // ensures ViewModel processes the update
            // next emission should contain 2 tasks
            val updated = awaitItem()
            assertEquals(2, updated.tasks.size)

            cancelAndConsumeRemainingEvents()
        }
    }

}