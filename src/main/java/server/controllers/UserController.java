package server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.model.Book;
import server.model.User;
import server.repositories.BookRepository;
import server.repositories.UserRepository;
import server.security.UserDetailsProvider;
import server.services.BookService;

import java.util.List;

@RestController
public class UserController {

    private UserRepository userRepository;
    private UserDetailsProvider userService;

    public UserController(UserRepository userRepository, UserDetailsProvider userSerivce) {
        this.userRepository = userRepository;
        this.userService = userSerivce;
    }

    @GetMapping("/login")
    public ResponseEntity checkIfUserExists(@RequestParam(name = "login") String login){
        if (userRepository.findById(login).isPresent()){
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestParam(name = "login") String login,
                                            @RequestParam(name = "password") String password) {
        User user = new User (login, password);
        if (!userService.saveUser(user)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        else{
            return ResponseEntity.ok().build();
        }
    }
}
