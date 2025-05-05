package com.github.ellisepagong.database;

import com.github.ellisepagong.model.SavedTemplate;
import com.github.ellisepagong.model.Task;
import com.github.ellisepagong.model.Template;
import com.github.ellisepagong.model.TemplateTask;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @GetMapping("/templates/ret")
    public List<Template> searchTemplates(@RequestParam(name = "userId") Integer id,
                                          @RequestParam(name ="notArchived", required = false) Boolean notArchived,
                                          @RequestParam(name ="templateId", required = false) Integer templateId){
        if ((id != null) && (id > 0)){
            if (notArchived!= null){
                return templateRepository.findByUserIdAndArchivedFalse(id);
            }
            if((templateId != null) && (templateId > 0)){
                return templateRepository.findByUserIdAndTemplateId(id, templateId);
            }
            return templateRepository.findByUserId(id);
        }
        return new ArrayList<>();

    }

    // POST
    @PostMapping("/templates") //TODO: test
    public Template createNewTemplate(@RequestBody Template template){
        Template newTemplate = this.templateRepository.save(template); // returns same object but with id
        return newTemplate;
    }

    @PostMapping("/templates/saved/{savedTemplateId}") // TODO test
        public Template newTemplateFromSaved(@PathVariable("savedTemplateId") Integer savedtemplateId,
                                             @RequestBody Map<String, Object> date){
        Optional<SavedTemplate> savedTemplateOptional = this.savedTemplateRepository.findBySavedTemplateId(savedtemplateId);
        if(date.containsKey("templateDate")){
            if(savedTemplateOptional.isPresent()){
                SavedTemplate savedTemplate = savedTemplateOptional.get();

                Template newTemplate = new Template();

                newTemplate.setSavedTemplateId(savedTemplate.getSavedTemplateId()); // saved id
                newTemplate.setName(savedTemplate.getTemplateName());
                newTemplate.setUserId(savedTemplate.getUserId());
                try {
                    LocalDate parsedDate = LocalDate.parse((String) date.get("templateDate"));
                    Date sqlDate = Date.valueOf(parsedDate);

                    if (!sqlDate.before(Date.valueOf(LocalDate.now()))) {
                        newTemplate.setDate(sqlDate);
                    } else {
                        return null; // Invalid date
                    }
                } catch (Exception e) {
                    return null; // Invalid format
                }

                List<TemplateTask> templateTasks = this.templateTaskRepository.findByTemplateId(savedtemplateId);
                if (templateTasks.isEmpty()){
                    return null; // no templateTasks associated
                }
                newTemplate =  this.templateRepository.save(newTemplate);
                for(int i = 0; i<templateTasks.size(); i++){
                    Task newTask = getTask(templateTasks, i, newTemplate);
                    this.taskRepository.save(newTask);
                }

                return newTemplate;
            }
        }
        return null;
    }

    private static Task getTask(List<TemplateTask> templateTasks, int i, Template newTemplate) {
        TemplateTask tempTemplateTask = templateTasks.get(i);
        Task newTask = new Task();

        newTask.setUserId(tempTemplateTask.getUserId());
        newTask.setTaskName(tempTemplateTask.getTemplateTaskName());
        newTask.setTaskDesc(tempTemplateTask.getTemplateTaskDesc());
        newTask.setTemplate(true);
        newTask.setTemplateId(newTemplate.getTemplateId());
        newTask.setTaskDate(newTemplate.getDate());
        return newTask;
    }


    // PUT
    @PutMapping("/templates/{id}") //TODO: test
    public Template updateTemplate(@PathVariable("id") Integer id, @RequestBody Template t) {
        Optional<Template> templateToUpdateOptional = this.templateRepository.findById(id);
        if (!templateToUpdateOptional.isPresent()) { //checks if id is valid
            return null;
        }

        Template templateToUpdate = templateToUpdateOptional.get();

        if (t.getUserId() != null) {
            templateToUpdate.setUserId(t.getUserId());
        }

        if (t.getSavedId() != null) {
            templateToUpdate.setSavedTemplateId(t.getSavedId());
        }

        if (t.getName() != null) {
            templateToUpdate.setName(t.getName());
        }

        if (t.getDate() != null) {
            templateToUpdate.setDate(t.getDate());
        }

        if (t.getArchived() != null) {
            templateToUpdate.setArchived(t.getArchived());
        }

        return templateToUpdate;
    }

    // DELETE

    @DeleteMapping("/templates/{id}") // TODO: test
    public Template deleteTemplate(@PathVariable("id") Integer id){
        Optional<Template> templateToDeleteOptional = this.templateRepository.findById(id);
        if (!templateToDeleteOptional.isPresent()){ //checks if id is valid
            return null;
        }
        Template templateToDelete = templateToDeleteOptional.get();
        this.templateRepository.delete(templateToDelete);
        return templateToDelete;
    }


}
