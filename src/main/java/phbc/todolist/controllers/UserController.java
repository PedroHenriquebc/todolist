package phbc.todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phbc.todolist.models.UserModel;
import phbc.todolist.repositories.UserRepository;

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
            var userCreated = userRepository.save(userModel);
            return ResponseEntity.status(HttpStatus.OK).body(userCreated);
        }
    }
}
