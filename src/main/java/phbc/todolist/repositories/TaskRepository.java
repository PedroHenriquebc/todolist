package phbc.todolist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import phbc.todolist.models.TaskModel;
import phbc.todolist.models.UserModel;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByIdUser(UUID idUser);
}
