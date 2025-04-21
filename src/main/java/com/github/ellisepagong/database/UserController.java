package com.github.ellisepagong.database;

import com.github.ellisepagong.model.Template;
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

    @GetMapping("/users/ret") //TODO: test
    public List<User> searchUser(@RequestParam(name = "userId", required = false) Integer id,
                                 @RequestParam(name = "username", required = false) String username){

        if((id != null) && (id > 0)){
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()){
                return List.of(user.get());
            }else {
                return null;
            }
        }
        if(username != null){
            return userRepository.findByUsername(username);
        }

        return null;
    }

    //POST

    @PostMapping("/users") //TODO: test
    public User createNewUser(@RequestBody User user){
        User newUser = this.userRepository.save(user); // returns same object but with id
        return newUser;
    }

    //PUT

    @PutMapping("/users/{id}") //TODO: test
    public User updateUser(@PathVariable("id") Integer id, @RequestBody User u) {
        Optional<User> userToUpdateOptional = this.userRepository.findById(id);
        if (!userToUpdateOptional.isPresent()) { //checks if id is valid
            return null;
        }

        User userToUpdate = userToUpdateOptional.get();

        if (u.getFirstName() != null) {
            userToUpdate.setFirstName(u.getFirstName());
        }

        if (u.getLastName() != null) {
            userToUpdate.setLastName(u.getLastName());
        }

        if (u.getEmail() != null) {
            userToUpdate.setEmail(u.getEmail());
        }

        if (u.getPassword() != null) {
            userToUpdate.setPassword(u.getPassword());
        }

        if (u.getUsername() != null) {
            userToUpdate.setUsername(u.getUsername());
        }
        return userToUpdate;
    }

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
