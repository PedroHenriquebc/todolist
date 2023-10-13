package phbc.todolist.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phbc.todolist.models.TaskModel;
import phbc.todolist.models.UserModel;
import phbc.todolist.repositories.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);
        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início/término não pode ser uma data passada");
        }
        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início não pode ser depois que a data de término");
        }
        var task = taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }

    @GetMapping
    public ResponseEntity getAll(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        var taskList = taskRepository.findByIdUser((UUID) idUser);
        return ResponseEntity.status(HttpStatus.OK).body(taskList);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request) {
        var task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada");
        }
        var idUser = request.getAttribute("idUser");
        if (!task.getIdUser().equals(idUser)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O usuário não tem permissão para alterar esta tarefa");
        }
        taskModel.setIdUser((UUID) idUser);
        taskModel.setId(id);
        var savedTask = taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
}
