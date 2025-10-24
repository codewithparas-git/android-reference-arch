import app.cash.turbine.test
import com.codewithparas.core.common.model.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EditTaskViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var fakeRepo: FakeTaskRepository
    private lateinit var viewModel: EditTaskViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepo = FakeTaskRepository()
        viewModel = EditTaskViewModel(fakeRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadTask should update uiState when task exists`() = runTest {
        val task = Task(id = 1, title = "Task 1")


        viewModel.uiState.test {
            // Initially, uiState is empty
            assertEquals(null, awaitItem().taskId)

            fakeRepo.emitTaskById(task)

            viewModel.loadTask(1)
            advanceUntilIdle()

            val state = awaitItem()
            assertEquals(1, state.taskId)
            assertEquals("Task 1", state.title)
            assertEquals("Task 1", state.originalTitle)
            assertTrue(state.isValid)
            assertTrue(!state.isModified)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `onTitleChange updates title, isValid and isModified correctly`() = runTest {
        viewModel.onTitleChange("New Title")

        val state = viewModel.uiState.value
        assertEquals("New Title", state.title)
        assertTrue(state.isValid)
        assertTrue(state.isModified)
    }

    @Test
    fun `saveTask calls addTask when taskId is null`() = runTest {
        viewModel.onTitleChange("New Task")
        viewModel.saveTask()
        advanceUntilIdle()

        val added = fakeRepo.getAddedTasks()
        assertEquals(1, added.size)
        assertEquals("New Task", added[0].title)
    }

    @Test
    fun `saveTask calls updateTask when taskId is not null`() = runTest {
        val task = Task(id = 1, title = "Task 1")
        fakeRepo.emitTaskById(task)
        viewModel.loadTask(1)
        advanceUntilIdle()

        viewModel.onTitleChange("Updated Task")
        viewModel.saveTask()
        advanceUntilIdle()

        val updated = fakeRepo.getUpdatedTasks()
        assertEquals(1, updated.size)
        assertEquals("Updated Task", updated[0].title)
        assertEquals(1, updated[0].id)
    }

    @Test
    fun `deleteTask calls deleteTask when taskId is not null`() = runTest {
        val task = Task(id = 1, title = "Task 1")
        fakeRepo.emitTaskById(task)
        viewModel.loadTask(1)
        advanceUntilIdle()

        viewModel.deleteTask()
        advanceUntilIdle()

        val deleted = fakeRepo.getDeletedTasks()
        assertEquals(1, deleted.size)
        assertEquals(1, deleted[0].id)
    }

    @Test
    fun `loadTask with null id does not update uiState`() = runTest {
        val initialState = viewModel.uiState.value

        viewModel.loadTask(null)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertEquals(initialState, state)
    }
}
