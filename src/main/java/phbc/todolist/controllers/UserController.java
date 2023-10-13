package phbc.todolist.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phbc.todolist.models.UserModel;
import phbc.todolist.repositories.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity create(@RequestBody UserModel userModel) {
        var user = userRepository.findByUsername(userModel.getUsername());
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        } else {
            var passwordHashered = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
            userModel.setPassword(passwordHashered);
            var userCreated = userRepository.save(userModel);
            return ResponseEntity.status(HttpStatus.OK).body(userCreated);
        }
    }

    @GetMapping
    public List<UserModel> getAll() {
        return userRepository.findAll();
    }
}
