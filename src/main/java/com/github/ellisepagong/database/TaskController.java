package com.github.ellisepagong.database;

import com.github.ellisepagong.model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // GET

    @GetMapping("/tasks")
    public Iterable<Task> getAllTasks(){
        return this.taskRepository.findAll();
    }

    @GetMapping("/tasks/ret")
    public List<Task> searchTask(@RequestParam(name = "userId") Integer id,
                                 @RequestParam(name ="notArchived", required = false) Boolean notArchived,
                                 @RequestParam(name ="templateId", required = false) Integer templateId){

        if ((id != null) && (id > 0)){ // checks id validity
            if (notArchived != null) {
                if((templateId != null) && (templateId > 0)){ // for selecting tasks associated with a template
                    return this.taskRepository.findByUserIdAndTemplateIdAndIsArchivedFalse(id, templateId); //TODO: test
                }
                return this.taskRepository.findByUserIdAndIsArchivedFalse(id); // tested curl "http://localhost:8080/tasks/ret?userId=1&notArchived=true"
            }
            return this.taskRepository.findByUserId(id); // tested
        }
        return new ArrayList<>();
    }

    // POST

    @PostMapping("/tasks") //TODO: test
    public Task createNewTask(@RequestBody Task task){
        Task newTask = this.taskRepository.save(task); // returns same object but with task_id
        return newTask;
    }


    // PUT

    @PutMapping("/tasks/{id}") //TODO: test
    public Task updateTask(@PathVariable("id") Integer id, @RequestBody Task t){
        Optional<Task> taskToUpdateOptional = this.taskRepository.findById(id);
        if (!taskToUpdateOptional.isPresent()){ //checks if task id is valid
            return null;
        }

        Task taskToUpdate = taskToUpdateOptional.get();

        if (t.getTaskName() != null) {
            taskToUpdate.setTaskName(t.getTaskName());
        }
        if (t.getTaskName() != null) {
            taskToUpdate.setTaskName(t.getTaskName());
        }

        if (t.getTaskDesc() != null) {
            taskToUpdate.setTaskDesc(t.getTaskDesc());
        }

        if (t.getTaskDate() != null) {
            taskToUpdate.setTaskDate(t.getTaskDate());
        }

        if (t.getTemplate() != null) {
            taskToUpdate.setTemplate(t.getTemplate());
        }

        if (t.getTemplateID() != null) {
            taskToUpdate.setTemplateID(t.getTemplateID());
        }

        if (t.isSaved() != null) {
            taskToUpdate.setSaved(t.isSaved());
        }

        if (t.getSaved_id() != null) {
            taskToUpdate.setSaved_id(t.getSaved_id());
        }

        if (t.isArchived() != null) {
            taskToUpdate.setArchived(t.isArchived());
        }

        this.taskRepository.save(taskToUpdate); // unsure?

        return taskToUpdate;
    }

    // DELETE

    @DeleteMapping("/tasks/{id}") // TODO: test
    public Task deleteTask(@PathVariable("id") Integer id){
        Optional<Task> taskToDeleteOptional = this.taskRepository.findById(id);
        if (!taskToDeleteOptional.isPresent()){ //checks if task id is valid
            return null;
        }
        Task taskToDelete = taskToDeleteOptional.get();
        this.taskRepository.delete(taskToDelete);
        return taskToDelete;
    }

}
