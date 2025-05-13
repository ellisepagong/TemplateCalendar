package com.github.ellisepagong.database;

import com.github.ellisepagong.model.SavedTask;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class SavedTaskController {

    private final SavedTaskRepository savedTaskRepository;

    public SavedTaskController(SavedTaskRepository savedTaskRepository) {
        this.savedTaskRepository = savedTaskRepository;
    }

    // GET

    @GetMapping("/savedTasks/{id}")
    public ResponseEntity<?> searchSavedTaskById(@PathVariable("id") int id){                                         // TESTED WITH POSTMAN
        Optional<SavedTask> savedTaskOptional =  this.savedTaskRepository.findBySavedTaskIdAndArchivedFalse(id);
        if (savedTaskOptional.isPresent()){
            return ResponseEntity.ok(savedTaskOptional.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Active Saved Task Found");
        }
    }

    @GetMapping("/savedTasks/ret")
    public ResponseEntity<?> searchSavedTask(@RequestParam(name = "userId") Integer id,
                                 @RequestParam(name ="notArchived", required = false) Boolean notArchived){

        if ((id != null) && (id > 0)){ // checks id validity
            if (notArchived != null) {
                return ResponseEntity.ok(this.savedTaskRepository.findByUserIdAndArchivedFalse(id));                                       // TESTED WITH POSTMAN
            }
            return ResponseEntity.ok(this.savedTaskRepository.findByUserId(id));                                                           // TESTED WITH POSTMAN
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not a valid id");
        }
    }

    //POST

    @PostMapping("/savedTasks")
    public ResponseEntity<?> createNewSavedTask(@RequestBody SavedTask task){                                                   // TESTED WITH POSTMAN
        SavedTask newTask = this.savedTaskRepository.save(task); // returns same object but with id
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    // PATCH
    @PatchMapping("/savedTasks/{id}")
    public ResponseEntity<?> updateSavedTask(@PathVariable("id") Integer id, @RequestBody Map<String, Object> updates) {        // TESTED WITH POSTMAN
        Optional<SavedTask> taskToUpdateOptional = savedTaskRepository.findById(id);
        if (!taskToUpdateOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Saved Task Found");
        }
        SavedTask taskToUpdate = taskToUpdateOptional.get();

        if (updates.containsKey("taskName")) {
            Object value = updates.get("taskName");
            if (value instanceof String) {
                taskToUpdate.setTaskName((String) value);
            }
        }


        if (updates.containsKey("taskDesc")) {
            Object value = updates.get("taskDesc");
            if (value instanceof String) {
                taskToUpdate.setTaskDesc((String) value);
            }
        }

        taskToUpdate = savedTaskRepository.save(taskToUpdate);

        return ResponseEntity.ok(taskToUpdate);
    }


    //DELETE

    @DeleteMapping("/savedTasks/{id}")
    public ResponseEntity<?> deleteSavedTask(@PathVariable("id") Integer id){                                                   // TESTED WITH POSTMAN
        Optional<SavedTask> taskToDeleteOptional = this.savedTaskRepository.findBySavedTaskIdAndArchivedFalse(id);
        if (!taskToDeleteOptional.isPresent()){ //checks if task id is valid
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Saved Task not Found");
        }
        SavedTask taskToDelete = taskToDeleteOptional.get();
        taskToDelete.setArchived(true);
        this.savedTaskRepository.save(taskToDelete);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
