import com.codewithparas.core.common.model.Task
import com.codewithparas.data.db.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeTaskRepository : ITaskRepository {
    private val tasksFlow = MutableStateFlow<List<Task>>(emptyList())
    private val taskByIdFlow = MutableStateFlow<Task?>(null)

    // Track added/updated/deleted tasks for verification
    private val addedTasks = mutableListOf<Task>()
    private val updatedTasks = mutableListOf<TaskEntity>()
    private val deletedTasks = mutableListOf<TaskEntity>()

    override fun getTasks(): Flow<List<Task>> = tasksFlow

    fun emitTasks(tasks: List<Task>) {
        tasksFlow.value = tasks
    }

    override suspend fun addTask(title: String) {
        val newTask = Task(id = tasksFlow.value.size + 1, title = title)
        tasksFlow.value = tasksFlow.value + newTask
        addedTasks.add(newTask)
    }

    override suspend fun updateTask(task: TaskEntity) {
        tasksFlow.value = tasksFlow.value.map {
            if (it.id == task.id) it.copy(title = task.title) else it
        }
        updatedTasks.add(task)
    }

    override suspend fun deleteTask(task: TaskEntity) {
        tasksFlow.value = tasksFlow.value.filter { it.id != task.id }
        deletedTasks.add(task)
    }

    override fun getTaskById(taskId: Int): Flow<Task?> = taskByIdFlow

    fun emitTaskById(task: Task?) {
        taskByIdFlow.value = task
    }

    // Helper getters for test assertions
    fun getAddedTasks(): List<Task> = addedTasks
    fun getUpdatedTasks(): List<TaskEntity> = updatedTasks
    fun getDeletedTasks(): List<TaskEntity> = deletedTasks
}
