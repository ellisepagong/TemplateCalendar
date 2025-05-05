package com.github.ellisepagong.database;

import com.github.ellisepagong.model.Template;
import com.github.ellisepagong.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// TODO: replace return null with HTTP response codes + error body

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //GET
    @GetMapping("/users")
    public Iterable<User> getAllUsers(){
        return this.userRepository.findAll();                                                                           //TESTED W POSTMAN
    }

    @GetMapping("/users/ret")                                                                                           //TESTED W POSTMAN
    public List<User> searchUser(@RequestParam(name = "userId") Integer id){

        if((id != null) && (id > 0)){
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()){
                return List.of(user.get());
            }else {
                return null;
            }
        }
        return null;
    }


    //POST
    @PostMapping("/users")
    public User createNewUser(@RequestBody User user){                                                                  //TESTED W POSTMAN
        User newUser = this.userRepository.save(user);
        return newUser;
    }

    @PostMapping("/users/login")
    public User authenticate(@RequestBody Map<String, Object> details){                                                 //TESTED W POSTMAN
        if (details.containsKey("username") && details.containsKey("password")) {
            Optional<User> userOptional = this.userRepository.findByUsername(String.valueOf(details.get("username")));
            if (userOptional.isPresent()) { //TODO: add hashing
                User user = userOptional.get();
                String pass = user.getPassword();
                if (pass.equals(String.valueOf(details.get("password")))){
                    return user;
                }
            }
        }
        return null;
    }

    // PATCH
    @PatchMapping("/users/{id}")
    public User updateUserFields(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {                  //TESTED W POSTMAN
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (updates.containsKey("lastName")) {
                user.setLastName((String) updates.get("lastName"));
            }
            if (updates.containsKey("firstName")) {
                user.setFirstName((String) updates.get("firstName"));
            }

            return userRepository.save(user);
        }


        return null ;
    }


    //DELETE
    @DeleteMapping("/users/{id}")                                                                                       //TESTED W POSTMAN
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
