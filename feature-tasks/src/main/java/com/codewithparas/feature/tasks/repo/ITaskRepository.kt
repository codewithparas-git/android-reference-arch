import com.codewithparas.core.common.model.Task
import com.codewithparas.data.db.TaskEntity
import kotlinx.coroutines.flow.Flow

interface ITaskRepository {
    fun getTasks(): Flow<List<Task>>
    suspend fun addTask(title: String)
    suspend fun updateTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
    fun getTaskById(taskId: Int): Flow<Task?>
}
