package com.github.ellisepagong.database;

import com.github.ellisepagong.model.TemplateTask;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TemplateTaskController {

    private final TemplateTaskRepository templateTaskRepository;

    public TemplateTaskController(TemplateTaskRepository templateTaskRepository) {
        this.templateTaskRepository = templateTaskRepository;
    }

    // GET

    @GetMapping("/templateTasks")
    public Iterable<TemplateTask> getAllTemplateTasks(){
        return this.templateTaskRepository.findAll();                                                                   // TESTED WITH POSTMAN
    }

    @GetMapping("/templateTasks/{templateTaskId}")
    public Optional<TemplateTask> searchTemplateTaskById(@PathVariable("templateTaskId") int id){                                   // TESTED WITH POSTMAN
        return this.templateTaskRepository.findById(id);
    }

    @GetMapping("/templateTasks/ret")
    public List<TemplateTask> searchTemplateTasks(@RequestParam(name = "userId", required = false) Integer userId,
                                                  @RequestParam(name ="templateId", required = false) Integer templateId){

        if(userId != null){
            return this.templateTaskRepository.findByUserId(userId);                                                    // TESTED IN POSTMAN
        }
        if(templateId != null){
            return this.templateTaskRepository.findByTemplateId(templateId);                                            // TESTED IN POSTMAN
        }
        return new ArrayList<>();
    }

    // POST

    @PostMapping("/templateTasks")
    public Iterable<TemplateTask> createNewSavedTask(@RequestBody List<TemplateTask> tasks){                            // TESTED WITH POSTMAN
        Integer refUserId = tasks.get(0).getUserId();
        Integer refTemplateId = tasks.get(0).getTemplateId();

        for (int i = 0; i < tasks.size(); i++) {
            TemplateTask task = tasks.get(i);

            if (!task.getUserId().equals(refUserId) || !task.getTemplateId().equals(refTemplateId)) {
                return null;
            }
        }
        return templateTaskRepository.saveAll(tasks);
    }

    // PATCH
    @PatchMapping("/templateTasks/{templateTaskId}")
    public TemplateTask updateTemplateTask(@PathVariable("templateTaskId") Integer taskId,                              // TESTED WITH POSTMAN
                                           @RequestBody Map<String, Object> updates){
        Optional<TemplateTask> templateTaskOptional = this.templateTaskRepository.findByTemplateTaskId(taskId);

        if(templateTaskOptional.isPresent()){

            TemplateTask templateTaskToUpdate = templateTaskOptional.get();

            if (updates.containsKey("taskName")) {
                Object value = updates.get("taskName");
                if (value instanceof String) {
                    templateTaskToUpdate.setTemplateTaskName((String) value);
                }
            }


            if (updates.containsKey("taskDesc")) {
                Object value = updates.get("taskDesc");
                if (value instanceof String) {
                    templateTaskToUpdate.setTemplateTaskDesc((String) value);
                }
            }

            if (updates.containsKey("archived")) {
                Object value = updates.get("archived");
                if (value instanceof Boolean) {
                    templateTaskToUpdate.setArchived((Boolean) value);
                } else if (value instanceof String) {
                    templateTaskToUpdate.setArchived(Boolean.parseBoolean((String) value));
                }
            }

            templateTaskRepository.save(templateTaskToUpdate);

            return templateTaskToUpdate;
        }

        return null;
    }

    // delete

    @DeleteMapping("/templateTasks/{templateTaskId}")
    public TemplateTask deleteTemplateTask(@PathVariable("templateTaskId") Integer taskId){                             // TESTED IN POSTMAN
        Optional<TemplateTask> templateTaskToDeleteOptional = this.templateTaskRepository.findByTemplateTaskId(taskId);

        if (templateTaskToDeleteOptional.isPresent()){
            TemplateTask templateTaskToDelete = templateTaskToDeleteOptional.get();
            this.templateTaskRepository.delete(templateTaskToDelete);
            return templateTaskToDelete;
        }
        return null;
    }




}
