import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithparas.core.common.model.Task
import com.codewithparas.data.db.toEntity
import com.codewithparas.feature.tasks.repo.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditTaskViewModel(private val repo: TaskRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(EditTaskUiState())
    val uiState: StateFlow<EditTaskUiState> = _uiState

    fun loadTask(taskId: Int?) {
        if (taskId != null) {
            viewModelScope.launch {
                repo.getTaskById(taskId).collect { task ->
                    if (task != null) {
                        _uiState.value = _uiState.value.copy(
                            taskId = task.id,
                            title = task.title,
                            originalTitle = task.title,
                            isValid = task.title.isNotBlank(),
                            isModified = false
                        )
                    }
                }
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _uiState.value = _uiState.value.copy(
            title = newTitle,
            isValid = newTitle.isNotBlank(),
            isModified = newTitle != _uiState.value.originalTitle
        )
    }

    fun saveTask() {
        viewModelScope.launch {
            if (_uiState.value.taskId == null) {
                repo.addTask(_uiState.value.title)
            } else {
                _uiState.value.taskId?.let {
                    repo.updateTask(Task(id = it, title = _uiState.value.title).toEntity())
                }
            }
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            _uiState.value.taskId?.let {
                repo.deleteTask(Task(id = it, title = _uiState.value.title).toEntity())
            }
        }
    }
}

data class EditTaskUiState(
    val taskId: Int? = null,
    val title: String = "",
    val originalTitle: String = "",
    val isValid: Boolean = false,
    val isModified: Boolean = false
)
