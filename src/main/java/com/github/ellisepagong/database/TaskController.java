package com.github.ellisepagong.database;

import com.github.ellisepagong.model.SavedTask;
import com.github.ellisepagong.model.Task;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TaskController {

    private final TaskRepository taskRepository;
    private final SavedTaskRepository savedTaskRepository;

    public TaskController(final TaskRepository taskRepository, SavedTaskRepository savedTaskRepository) {
        this.taskRepository = taskRepository;
        this.savedTaskRepository = savedTaskRepository;
    }

    // GET
    @GetMapping("/tasks/{id}")
    public Optional<Task> searchTaskById(@PathVariable("id") int id){                                                   //TESTED WITH POSTMAN
        return this.taskRepository.findById(id);
    }

    @GetMapping("/tasks/ret")
    public List<Task> searchTask(@RequestParam(name = "userId") Integer userId,
                                 @RequestParam(name ="notArchived", required = false) Boolean notArchived,
                                 @RequestParam(name ="templateId", required = false) Integer templateId){

        if ((userId != null) && (userId > 0)){ // checks id validity
            if (notArchived != null) {
                if((templateId != null) && (templateId > 0)){ // for selecting tasks associated with a template
                    return this.taskRepository.findByUserIdAndArchivedFalseAndTemplateId(userId, templateId);           // TODO: Test with templates
                }
                return this.taskRepository.findByUserIdAndArchivedFalse(userId);                                        //TESTED WITH POSTMAN
            }
            return this.taskRepository.findByUserId(userId);                                                            //TESTED WITH POSTMAN
        }
        return new ArrayList<>();
    }

    // POST
    @PostMapping("/tasks")
    public Task createNewTask(@RequestBody Task task){                                                                  // TESTED WITH POSTMAN
        if (task.isDateValid()){                                                                                        // TESTED WITH POSTMAN
            Task newTask = this.taskRepository.save(task); // returns same object but with task_id
            return newTask;
        }
        return null;
    }

    @PostMapping("/tasks/saved/{savedId}")
    public Task newTaskFromSaved(@PathVariable("savedId") Integer savedId, @RequestBody Map<String, Object> date){      // TESTED WITH POSTMAN
        Optional<SavedTask> savedTaskOptional = this.savedTaskRepository.findBySavedTaskId(savedId);
        if(date.containsKey("taskDate")) {
            if (savedTaskOptional.isPresent()) {
                SavedTask savedTask = savedTaskOptional.get();

                Task newTask = new Task();

                newTask.setUserId(savedTask.getUserId());
                newTask.setTaskName(savedTask.getTaskName());
                newTask.setTaskDesc(savedTask.getTaskDesc());
                try {
                    LocalDate parsedDate = LocalDate.parse((String) date.get("taskDate"));
                    Date sqlDate = Date.valueOf(parsedDate);

                    if (!sqlDate.before(Date.valueOf(LocalDate.now()))) {
                        newTask.setTaskDate(sqlDate);
                    } else {
                        return null; // Invalid date
                    }
                } catch (Exception e) {
                    return null; // Invalid format
                }
                newTask.setSaved(true);
                newTask.setSavedId(savedId);

                return this.taskRepository.save(newTask);
            }
        }
        return null;
    }

    // PATCH
    @PatchMapping("/tasks/{id}")
    public Task patchTask(@PathVariable("id") Integer id, @RequestBody Map<String, Object> updates) {                   //TESTED WITH POSTMAN
        Optional<Task> taskToUpdateOptional = this.taskRepository.findById(id);
        if (!taskToUpdateOptional.isPresent()) {
            return null;
        }

        Task taskToUpdate = taskToUpdateOptional.get();

        if (updates.containsKey("taskName")) {
            taskToUpdate.setTaskName((String) updates.get("taskName"));
        }

        if (updates.containsKey("taskDesc")) {
            taskToUpdate.setTaskDesc((String) updates.get("taskDesc"));
        }

        if (updates.containsKey("taskDate")) {
            try {
                LocalDate parsedDate = LocalDate.parse((String) updates.get("taskDate"));
                Date sqlDate = Date.valueOf(parsedDate);

                if (!sqlDate.before(Date.valueOf(LocalDate.now()))) {
                    taskToUpdate.setTaskDate(sqlDate);
                } else {
                    return null; // Invalid date
                }
            } catch (Exception e) {
                return null; // Invalid format
            }
        }

        this.taskRepository.save(taskToUpdate);
        return taskToUpdate;
    }


    // DELETE

    @DeleteMapping("/tasks/{id}")
    public Task deleteTask(@PathVariable("id") Integer id){                                                             // TESTED WITH POSTMAN
        Optional<Task> taskToDeleteOptional = this.taskRepository.findById(id);
        if (!taskToDeleteOptional.isPresent()){ //checks if task id is valid
            return null;
        }
        Task taskToDelete = taskToDeleteOptional.get();
        taskToDelete.setArchived(true);
        return taskToDelete;
    }

}
