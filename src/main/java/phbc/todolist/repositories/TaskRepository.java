package phbc.todolist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import phbc.todolist.models.TaskModel;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
}
