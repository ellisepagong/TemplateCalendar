package com.github.ellisepagong.database;

import com.github.ellisepagong.model.SavedTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> searchSavedTaskById(@PathVariable("id") int id) {
        Optional<SavedTask> savedTaskOptional = this.savedTaskRepository.findBySavedTaskIdAndArchivedFalse(id);
        if (savedTaskOptional.isPresent()) {
            return ResponseEntity.ok(savedTaskOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Saved Task Found");
        }
    }

    @GetMapping("/savedTasks/")
    public ResponseEntity<?> searchSavedTask(@RequestParam(name = "userId", required = false) Integer id) {

        if (id != null) {
            List<SavedTask> savedTaskList = this.savedTaskRepository.findByUserIdAndArchivedFalse(id);
            if (savedTaskList.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Saved Tasks Found");
            }else{
                return ResponseEntity.ok(savedTaskList);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments");
        }
    }

    //POST

    @PostMapping("/savedTasks")
    public ResponseEntity<?> createNewSavedTask(@RequestBody SavedTask task) {
        SavedTask newTask = this.savedTaskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    // PATCH
    @PatchMapping("/savedTasks/{id}")
    public ResponseEntity<?> updateSavedTask(@PathVariable("id") Integer id, @RequestBody Map<String, Object> updates) {
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
    public ResponseEntity<?> deleteSavedTask(@PathVariable("id") Integer id) {
        Optional<SavedTask> taskToDeleteOptional = this.savedTaskRepository.findBySavedTaskIdAndArchivedFalse(id);
        if (!taskToDeleteOptional.isPresent()) { //checks if task id is valid
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Saved Task not Found");
        }
        SavedTask taskToDelete = taskToDeleteOptional.get();
        taskToDelete.setArchived(true);
        this.savedTaskRepository.save(taskToDelete);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
