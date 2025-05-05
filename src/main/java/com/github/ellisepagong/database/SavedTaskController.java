package com.github.ellisepagong.database;

import com.github.ellisepagong.model.SavedTask;
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
    public Optional<SavedTask> searchSavedTaskById(@PathVariable("id") int id){                                         // TESTED WITH POSTMAN
        return this.savedTaskRepository.findById(id);
    }

    @GetMapping("/savedTasks/ret")
    public List<SavedTask> searchSavedTask(@RequestParam(name = "userId") Integer id,
                                 @RequestParam(name ="notArchived", required = false) Boolean notArchived){

        if ((id != null) && (id > 0)){ // checks id validity
            if (notArchived != null) {
                return this.savedTaskRepository.findByUserIdAndArchivedFalse(id);                                       // TESTED WITH POSTMAN
            }
            return this.savedTaskRepository.findByUserId(id);                                                           // TESTED WITH POSTMAN
        }
        return new ArrayList<>();
    }

    //POST

    @PostMapping("/savedTasks")
    public SavedTask createNewSavedTask(@RequestBody SavedTask task){                                                   // TESTED WITH POSTMAN
        SavedTask newTask = this.savedTaskRepository.save(task); // returns same object but with id
        return newTask;
    }

    // PATCH
    @PatchMapping("/savedTasks/{id}")
    public SavedTask updateSavedTask(@PathVariable("id") Integer id, @RequestBody Map<String, Object> updates) {        // TESTED WITH POSTMAN
        Optional<SavedTask> taskToUpdateOptional = savedTaskRepository.findById(id);
        if (!taskToUpdateOptional.isPresent()) {
            return null;
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


        if (updates.containsKey("archived")) {
            Object value = updates.get("archived");
            if (value instanceof Boolean) {
                taskToUpdate.setArchived((Boolean) value);
            } else if (value instanceof String) {
                taskToUpdate.setArchived(Boolean.parseBoolean((String) value));
            }
        }

        savedTaskRepository.save(taskToUpdate);

        return taskToUpdate;
    }


    //DELETE

    @DeleteMapping("/savedTasks/{id}")
    public SavedTask deleteSavedTask(@PathVariable("id") Integer id){                                                   // TESTED WITH POSTMAN
        Optional<SavedTask> taskToDeleteOptional = this.savedTaskRepository.findById(id);
        if (!taskToDeleteOptional.isPresent()){ //checks if task id is valid
            return null;
        }
        SavedTask taskToDelete = taskToDeleteOptional.get();
        taskToDelete.setArchived(true);
        return taskToDelete;
    }
}
