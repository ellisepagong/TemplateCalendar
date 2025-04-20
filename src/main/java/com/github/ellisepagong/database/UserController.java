package com.github.ellisepagong.database;

import com.github.ellisepagong.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //GET
    @GetMapping("/users") // TODO: test
    public Iterable<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    @GetMapping("/users/ret")
    public List<User> searchUser(@RequestParam(name = "userId", required = false) Integer id,
                                 @RequestParam(name = "username", required = false) String username){

        // TODO

        return new ArrayList<>();
    }

    //POST

    @PostMapping("/users") //TODO: test
    public User createNewUser(@RequestBody User user){
        User newUser = this.userRepository.save(user); // returns same object but with id
        return newUser;
    }

    //PUT

    //TODO

    //DELETE

    @DeleteMapping("/users/{id}") // TODO: test
    public User deleteTask(@PathVariable("id") Integer id){
        Optional<User> userToDeleteOptional = this.userRepository.findById(id);
        if (!userToDeleteOptional.isPresent()){ //checks if id is valid
            return null;
        }
        User userToDelete = userToDeleteOptional.get();
        this.userRepository.delete(userToDelete);
        return userToDelete;
    }
}
