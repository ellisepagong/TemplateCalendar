package com.github.ellisepagong.database;

import com.github.ellisepagong.model.SavedTask;
import com.github.ellisepagong.model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class SavedTaskController {

    private final SavedTaskRepository savedTaskRepository;

    public SavedTaskController(SavedTaskRepository savedTaskRepository) {
        this.savedTaskRepository = savedTaskRepository;
    }

    // GET

    @GetMapping("/savedTasks")
    public Iterable<SavedTask> getAllSavedTasks(){
        return this.savedTaskRepository.findAll();
    }

    @GetMapping("/savedTasks/{id}")
    public Optional<SavedTask> searchSavedTaskById(@PathVariable("id") int id){
        return this.savedTaskRepository.findById(id);
    }

    @GetMapping("/savedTasks/ret")
    public List<SavedTask> searchSavedTask(@RequestParam(name = "userId") Integer id,
                                 @RequestParam(name ="notArchived", required = false) Boolean notArchived,
                                 @RequestParam(name ="taskId", required = false) Integer taskId){

        if ((id != null) && (id > 0)){ // checks id validity
            if (notArchived != null) {
                if((taskId != null) && (taskId > 0)){ // for selecting tasks associated with a template
                    return this.savedTaskRepository.findByUserIdAndSavedTaskIdAndArchivedFalse(id, taskId); //TODO: test
                }
                return this.savedTaskRepository.findByUserIdAndArchivedFalse(id); // todo: test
            }
            return this.savedTaskRepository.findByUserId(id); // todo: test
        }
        return new ArrayList<>();
    }

    //POST

    @PostMapping("/savedTasks") //TODO: test
    public SavedTask createNewSavedTask(@RequestBody SavedTask task){
        SavedTask newTask = this.savedTaskRepository.save(task); // returns same object but with id
        return newTask;
    }

    //PUT

    @PutMapping("/savedTasks/{id}") //TODO: test
    public SavedTask updateSavedTask(@PathVariable("id") Integer id, @RequestBody SavedTask t){
        Optional<SavedTask> taskToUpdateOptional = this.savedTaskRepository.findById(id);
        if (!taskToUpdateOptional.isPresent()){ //checks if task id is valid
            return null;
        }

        SavedTask taskToUpdate = taskToUpdateOptional.get();

        if (t.getTaskName() != null) {
            taskToUpdate.setTaskName(t.getTaskName());
        }

        if (t.getArchived() != null) {
            taskToUpdate.setArchived(t.getArchived());
        }

        if (t.getTaskDescription() != null) {
            taskToUpdate.setTaskDescription(t.getTaskDescription());
        }

        this.savedTaskRepository.save(taskToUpdate); // unsure?

        return taskToUpdate;
    }


    //DELETE

    @DeleteMapping("/savedTasks/{id}") // TODO: test
    public SavedTask deleteSavedTask(@PathVariable("id") Integer id){
        Optional<SavedTask> taskToDeleteOptional = this.savedTaskRepository.findById(id);
        if (!taskToDeleteOptional.isPresent()){ //checks if task id is valid
            return null;
        }
        SavedTask taskToDelete = taskToDeleteOptional.get();
        this.savedTaskRepository.delete(taskToDelete);
        return taskToDelete;
    }
}
