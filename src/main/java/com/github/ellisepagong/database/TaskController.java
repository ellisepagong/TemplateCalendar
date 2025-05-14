package com.github.ellisepagong.database;

import com.github.ellisepagong.model.SavedTask;
import com.github.ellisepagong.model.Task;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> searchTaskById(@PathVariable("id") int id){                                             //TESTED WITH POSTMAN
        Optional<Task> findTask = this.taskRepository.findByTaskIdAndArchivedFalse(id);
        if (findTask.isPresent()){
            return ResponseEntity.ok(findTask.get());
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Active task by that ID");
        }
    }

    @GetMapping("/tasks/ret")
    public ResponseEntity<?> searchTask(@RequestParam(name = "userId", required = false) Integer userId,
                                        @RequestParam(name ="templateId", required = false) Integer templateId){

        if ((userId != null)){ // checks id validity
            return ResponseEntity.ok(this.taskRepository.findByUserIdAndArchivedFalse(userId));
        }else if(templateId!=null){
            return ResponseEntity.ok(this.taskRepository.findByTemplateIdAndArchivedFalse(templateId));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Parameters. Please include either userId or templateId");
        }

    }

    // POST
    @PostMapping("/tasks")
    public ResponseEntity<?> createNewTask(@RequestBody Task task){                                                                  // TESTED WITH POSTMAN
        if (task.isDateValid()){                                                                                        // TESTED WITH POSTMAN
            Task newTask = this.taskRepository.save(task); // returns same object but with task_id
            return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Date");
    }

    @PostMapping("/tasks/saved/{savedId}")
    public ResponseEntity<?> newTaskFromSaved(@PathVariable("savedId") Integer savedId, @RequestBody Map<String, Object> date){      // TESTED WITH POSTMAN
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
                        ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Date");
                    }
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect date format"); // Invalid format
                }
                newTask.setSaved(true);
                newTask.setSavedId(savedId);

                return ResponseEntity.status(HttpStatus.CREATED).body(this.taskRepository.save(newTask));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No taskDate argument");
    }

    // PATCH
    @PatchMapping("/tasks/{id}")
    public ResponseEntity<?> patchTask(@PathVariable("id") Integer id, @RequestBody Map<String, Object> updates) {                   //TESTED WITH POSTMAN
        Optional<Task> taskToUpdateOptional = this.taskRepository.findById(id);
        if (!taskToUpdateOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Task Found");
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
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Date"); // Invalid date
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect date format"); // Invalid format
            }
        }
        return ResponseEntity.ok(this.taskRepository.save(taskToUpdate));
    }


    // DELETE

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Integer id){                                                             // TESTED WITH POSTMAN
        Optional<Task> taskToDeleteOptional = this.taskRepository.findByTaskIdAndArchivedFalse(id);
        if (!taskToDeleteOptional.isPresent()){ //checks if task id is valid
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Task Found");
        }
        Task taskToDelete = taskToDeleteOptional.get();
        taskToDelete.setArchived(true);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
