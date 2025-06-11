package com.github.ellisepagong.database;

import com.github.ellisepagong.model.SavedTemplate;
import com.github.ellisepagong.model.Task;
import com.github.ellisepagong.model.Template;
import com.github.ellisepagong.model.TemplateTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class TemplateController {

    private final TemplateRepository templateRepository;
    private final SavedTemplateRepository savedTemplateRepository;
    private final TemplateTaskRepository templateTaskRepository;
    private final TaskRepository taskRepository;

    public TemplateController(TemplateRepository templateRepository, SavedTemplateRepository savedTemplateRepository, TemplateTaskRepository templateTaskRepository, TaskRepository taskRepository) {
        this.templateRepository = templateRepository;
        this.savedTemplateRepository = savedTemplateRepository;
        this.templateTaskRepository = templateTaskRepository;
        this.taskRepository = taskRepository;
    }


    // GET

    @GetMapping("/templates/")
    public ResponseEntity<?> searchTemplates(@RequestParam(value = "userId", required = false) Integer userId, @RequestParam(value = "templateId", required = false) Integer templateId) {
        if (userId != null) {
            List<Template> templateList = this.templateRepository.findByTemplateUserIdAndArchivedFalse(userId);
            if (templateList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Templates Found");
            } else {
                return ResponseEntity.ok(templateList);
            }
        } else if (templateId != null) {
            Optional<Template> templateOptional = this.templateRepository.findByTemplateIdAndArchivedFalse(templateId);
            if (templateOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Template Found");
            } else {
                return ResponseEntity.ok(templateOptional);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Parameters");
        }
    }

    // POST
    @PostMapping("/templates/saved/{savedTemplateId}") // TODO test
    public ResponseEntity<?> newTemplateFromSaved(@PathVariable("savedTemplateId") Integer savedtemplateId,
                                                  @RequestBody Map<String, Object> date) {
        Optional<SavedTemplate> savedTemplateOptional = this.savedTemplateRepository.findBySavedTemplateIdAndArchivedFalse(savedtemplateId);
        if (date.containsKey("templateDate")) {
            if (!savedTemplateOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Saved Template Found");
            }
            SavedTemplate savedTemplate = savedTemplateOptional.get();

            Template newTemplate = new Template();

            newTemplate.setSavedTemplateId(savedTemplate.getSavedTemplateId()); // saved id
            newTemplate.setTemplateName(savedTemplate.getTemplateName());
            newTemplate.setTemplateUserId(savedTemplate.getSavedTemplateUserId());
            try {
                LocalDate parsedDate = LocalDate.parse((String) date.get("templateDate"));
                Date sqlDate = Date.valueOf(parsedDate);

                if (!sqlDate.before(Date.valueOf(LocalDate.now()))) {
                    newTemplate.setTemplateDate(sqlDate);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Date");
                }
            } catch (Exception e) {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Date Format");
            }

            List<TemplateTask> templateTasks = this.templateTaskRepository.findBySavedTemplateTaskTemplateId(savedtemplateId);
            if (templateTasks.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Tasks found from Template");
            }
            newTemplate = this.templateRepository.save(newTemplate);
            for (int i = 0; i < templateTasks.size(); i++) {
                Task newTask = getTask(templateTasks, i, newTemplate);
                this.taskRepository.save(newTask);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(newTemplate);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No templateDate parameter");
        }
    }

    private static Task getTask(List<TemplateTask> templateTasks, int i, Template newTemplate) {
        TemplateTask tempTemplateTask = templateTasks.get(i);
        Task newTask = new Task();

        newTask.setTaskUserId(tempTemplateTask.getSavedTemplateTaskUserId());
        newTask.setTaskName(tempTemplateTask.getSavedTemplateTaskName());
        newTask.setTaskDesc(tempTemplateTask.getSavedTemplateTaskDesc());
        newTask.setTemplate(true);
        newTask.setTaskTemplateId(newTemplate.getTemplateId());
        newTask.setTaskDate(newTemplate.getTemplateDate());
        return newTask;
    }


    // PATCH
    @PatchMapping("/templates/{id}")
    public ResponseEntity<?> updateTemplate(@PathVariable("id") Integer id, @RequestBody Map<String, Object> updates) {
        Optional<Template> templateToUpdateOptional = this.templateRepository.findById(id);
        if (!templateToUpdateOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Template Found");
        }
        Template templateToUpdate = templateToUpdateOptional.get();
        if (updates.containsKey("taskDate")) {
            templateToUpdate.setTemplateDate((Date) updates.get("taskDate"));
        }

        return ResponseEntity.ok(this.templateRepository.save(templateToUpdate));
    }

    // DELETE

    @DeleteMapping("/templates/{id}") // TODO: test
    public ResponseEntity<?> deleteTemplate(@PathVariable("id") Integer id) {
        Optional<Template> templateToDeleteOptional = this.templateRepository.findByTemplateIdAndArchivedFalse(id);
        if (!templateToDeleteOptional.isPresent()) { //checks if id is valid
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Template Found");
        }
        Template templateToDelete = templateToDeleteOptional.get();
        templateToDelete.setArchived(true);
        List<Task> taskList = this.taskRepository.findByTaskTemplateIdAndArchivedFalse(templateToDelete.getTemplateId());
        for (int i = 0; i < taskList.size(); i++) {
            Task task = taskList.get(i);
            task.setArchived(true);
        }
        this.taskRepository.saveAll(taskList);
        this.templateRepository.save(templateToDelete);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }


}
