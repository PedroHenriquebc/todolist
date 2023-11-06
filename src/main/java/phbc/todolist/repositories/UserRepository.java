package phbc.todolist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import phbc.todolist.models.UserModel;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByUsername(String username);
}
