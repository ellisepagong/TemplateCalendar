package com.github.ellisepagong.controller;

import com.github.ellisepagong.repository.TemplateTaskRepository;
import com.github.ellisepagong.model.TemplateTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("templateTasks")
public class TemplateTaskController {

    private final TemplateTaskRepository templateTaskRepository;

    public TemplateTaskController(TemplateTaskRepository templateTaskRepository) {
        this.templateTaskRepository = templateTaskRepository;
    }

    // GET

    @GetMapping("/{templateTaskId}")
    public ResponseEntity<?> searchTemplateTaskById(@PathVariable("templateTaskId") int id) {
        Optional<TemplateTask> templateTaskOptional = this.templateTaskRepository.findBySavedTemplateTaskIdAndArchivedFalse(id);
        if (templateTaskOptional.isPresent()) {
            return ResponseEntity.ok(templateTaskOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Task found");
        }
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<?> searchTemplateTasks(@PathVariable("templateId") Integer templateId) {
        if (templateId != null) {
            List<TemplateTask> templateTaskList = this.templateTaskRepository.findBySavedTemplateTaskTemplateIdAndArchivedFalse(templateId);
            if(templateTaskList.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Tasks found for Template");
            }else{
                return ResponseEntity.ok(templateTaskList);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments. Please include userId or templateId");
        }
    }

    // POST

    @PostMapping
    public ResponseEntity<?> createNewSavedTask(@RequestBody List<TemplateTask> tasks) {                            // TESTED WITH POSTMAN
        Integer refUserId = tasks.get(0).getSavedTemplateTaskUserId();
        Integer refTemplateId = tasks.get(0).getSavedTemplateTaskTemplateId();

        for (int i = 0; i < tasks.size(); i++) {
            TemplateTask task = tasks.get(i);

            if (!task.getSavedTemplateTaskUserId().equals(refUserId) || !task.getSavedTemplateTaskTemplateId().equals(refTemplateId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User Id or Template Id does not match.");
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(templateTaskRepository.saveAll(tasks));
    }

    // PATCH
    @PatchMapping("/{templateTaskId}")
    public ResponseEntity<?> updateTemplateTask(@PathVariable("templateTaskId") Integer taskId,                              // TESTED WITH POSTMAN
                                                @RequestBody Map<String, Object> updates) {
        Optional<TemplateTask> templateTaskOptional = this.templateTaskRepository.findBySavedTemplateTaskIdAndArchivedFalse(taskId);

        if (templateTaskOptional.isPresent()) {

            TemplateTask templateTaskToUpdate = templateTaskOptional.get();

            if (updates.containsKey("taskName")) {
                Object value = updates.get("taskName");
                if (value instanceof String) {
                    templateTaskToUpdate.setSavedTemplateTaskName((String) value);
                }
            }


            if (updates.containsKey("taskDesc")) {
                Object value = updates.get("taskDesc");
                if (value instanceof String) {
                    templateTaskToUpdate.setSavedTemplateTaskDesc((String) value);
                }
            }

            return ResponseEntity.ok(templateTaskRepository.save(templateTaskToUpdate));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Template Task does not exist");
        }
    }

    // delete

    @DeleteMapping("/{templateTaskId}")
    public ResponseEntity<?> deleteTemplateTask(@PathVariable("templateTaskId") Integer taskId) {                             // TESTED IN POSTMAN
        Optional<TemplateTask> templateTaskToDeleteOptional = this.templateTaskRepository.findBySavedTemplateTaskIdAndArchivedFalse(taskId);

        if (templateTaskToDeleteOptional.isPresent()) {
            TemplateTask templateTaskToDelete = templateTaskToDeleteOptional.get();
            templateTaskToDelete.setArchived(true);
            return ResponseEntity.ok(this.templateTaskRepository.save(templateTaskToDelete));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Task Found");
        }
    }


}
