package com.github.ellisepagong.controller;

import com.github.ellisepagong.repository.SavedTaskRepository;
import com.github.ellisepagong.model.SavedTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/savedTasks")
public class SavedTaskController {

    private final SavedTaskRepository savedTaskRepository;

    public SavedTaskController(SavedTaskRepository savedTaskRepository) {
        this.savedTaskRepository = savedTaskRepository;
    }

    // GET

    @GetMapping("/{id}")
    public ResponseEntity<?> searchSavedTaskById(@PathVariable("id") int id) {
        Optional<SavedTask> savedTaskOptional = this.savedTaskRepository.findBySavedTaskIdAndArchivedFalse(id);
        if (savedTaskOptional.isPresent()) {
            return ResponseEntity.ok(savedTaskOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Saved Task Found");
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> searchSavedTask(@RequestParam(name = "userId", required = false) Integer id) {

        if (id != null) {
            List<SavedTask> savedTaskList = this.savedTaskRepository.findBySavedTaskUserIdAndArchivedFalse(id);
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

    @PostMapping()
    public ResponseEntity<?> createNewSavedTask(@RequestBody SavedTask task) {
        SavedTask newTask = this.savedTaskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    // PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateSavedTask(@PathVariable("id") Integer id, @RequestBody Map<String, Object> updates) {
        Optional<SavedTask> taskToUpdateOptional = savedTaskRepository.findById(id);
        if (taskToUpdateOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Saved Task Found");
        }
        SavedTask taskToUpdate = taskToUpdateOptional.get();

        if (updates.containsKey("savedTaskName")) {
            Object value = updates.get("savedTaskName");
            if (value instanceof String) {
                taskToUpdate.setSavedTaskName((String) value);
            }
        }

        if (updates.containsKey("savedTaskDesc")) {
            Object value = updates.get("savedTaskDesc");
            if (value instanceof String) {
                taskToUpdate.setSavedTaskDesc((String) value);
            }
        }

        taskToUpdate = savedTaskRepository.save(taskToUpdate);

        return ResponseEntity.ok(taskToUpdate);
    }


    //DELETE

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSavedTask(@PathVariable("id") Integer id) {
        Optional<SavedTask> taskToDeleteOptional = this.savedTaskRepository.findBySavedTaskIdAndArchivedFalse(id);
        if (taskToDeleteOptional.isEmpty()) { //checks if task id is valid
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Saved Task not Found");
        }
        SavedTask taskToDelete = taskToDeleteOptional.get();
        taskToDelete.setArchived(true);
        this.savedTaskRepository.save(taskToDelete);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
