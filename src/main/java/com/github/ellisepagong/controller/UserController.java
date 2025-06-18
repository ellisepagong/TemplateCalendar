package com.github.ellisepagong.controller;

import com.github.ellisepagong.repository.UserRepository;
import com.github.ellisepagong.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {



    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //GET
    @GetMapping("/{id}")
    public ResponseEntity<?> searchUser(@PathVariable Integer id) {
        if (id != null) {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No User Found");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No User Id Found");
        }
    }


//    //POST MOVED TO AuthController.java
//    @PostMapping("/users")
//    public ResponseEntity<?> createNewUser(@RequestBody User user) {
//        user.setPassword();
//        User newUser = this.userRepository.save(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
//    }
//
//    @PostMapping("/users/login")
//    public ResponseEntity<?> authenticate(@RequestBody Map<String, Object> details) {
//        if (details.containsKey("username") && details.containsKey("password")) {
//            Optional<User> userOptional = this.userRepository.findByEmail(String.valueOf(details.get("username")));
//            if (userOptional.isPresent()) {
//                User user = userOptional.get();
//                String pass = user.getPassword();
//                if (pass.equals(String.valueOf(details.get("password")))) {
//                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
//                } else {
//                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password does not match");
//                }
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username not Found");
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Username or Password Parameter found");
//        }
//    }

    // PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUserFields(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (updates.containsKey("lastName")) {
                user.setLastName((String) updates.get("lastName"));
            }
            if (updates.containsKey("firstName")) {
                user.setFirstName((String) updates.get("firstName"));
            }
            //todo: add password change

            return ResponseEntity.ok(userRepository.save(user));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found");
        }
    }


    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Integer id) {
        Optional<User> userToDeleteOptional = this.userRepository.findById(id);
        if (!userToDeleteOptional.isPresent()) { //checks if id is valid
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found");
        }
        User userToDelete = userToDeleteOptional.get();
        this.userRepository.delete(userToDelete);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
