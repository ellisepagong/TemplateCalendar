package com.github.ellisepagong.database;

import com.github.ellisepagong.model.SavedTemplate;
import com.github.ellisepagong.model.TemplateTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class SavedTemplateController  {

    private final SavedTemplateRepository savedTemplateRepository;
    private final TemplateTaskRepository templateTaskRepository;

    public SavedTemplateController(SavedTemplateRepository savedTemplateRepository, TemplateTaskRepository templateTaskRepository) {
        this.savedTemplateRepository = savedTemplateRepository;
        this.templateTaskRepository = templateTaskRepository;
    }

    // GET

    @GetMapping("/savedTemplates/{id}")
    ResponseEntity<?> searchSavedTemplate(@PathVariable("id") Integer savedTemplateId){
        Optional<SavedTemplate> savedTemplateOptional = this.savedTemplateRepository.findBySavedTemplateIdAndArchivedFalse(savedTemplateId);
        if (savedTemplateOptional.isPresent()){
            return ResponseEntity.ok(savedTemplateOptional.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Saved Template Found");
        }
    }

    @GetMapping("/savedTemplates/ret")
    ResponseEntity<?> searchSavedTemplates(@RequestParam(name = "id") Integer id,
                                                 @RequestParam(name = "templateId", required = false) Integer templateId,
                                                 @RequestParam(name = "archived", required = false) Boolean isArchived){
        if ((id != null) && (id > 0)){ // checks id validity
            if (isArchived != null) {
                return ResponseEntity.ok(this.savedTemplateRepository.findByUserIdAndArchivedFalse(id));                                   // TESTED IN POSTMAN
            }
            if((templateId != null) && (templateId > 0)){
                return ResponseEntity.ok(this.savedTemplateRepository.findByUserIdAndSavedTemplateId(id, templateId));                     // TESTED IN POSTMAN
            }
                return ResponseEntity.ok(this.savedTemplateRepository.findByUserId(id));                                                       // TESTED IN POSTMAN
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid User Id");
        }
    }

    // POST

    @PostMapping("/savedTemplates")
    public ResponseEntity<?> createNewSavedTemplate(@RequestBody SavedTemplate savedTemplate){                              // TESTED IN POSTMAN
        SavedTemplate newSavedTemplate = this.savedTemplateRepository.save(savedTemplate); // returns same object but with id
        return ResponseEntity.status(HttpStatus.CREATED).body(newSavedTemplate);
    }

    // PATCH
    @PatchMapping("/savedTemplates/{templateId}")
    public ResponseEntity<?> updateSavedTemplate(@PathVariable("templateId") Integer templateId,                            // TESTED WITH POSTMAN
                                             @RequestBody Map<String, Object> updates) {
        Optional<SavedTemplate> templateToUpdateOptional = this.savedTemplateRepository.findBySavedTemplateIdAndArchivedFalse(templateId);
        if (!templateToUpdateOptional.isPresent()) { //checks if id is valid
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Saved Template Found");
        }
        SavedTemplate templateToUpdate = templateToUpdateOptional.get();

        if (updates.containsKey("templateName")) {
            templateToUpdate.setTemplateName((String) updates.get("templateName"));
        }

        return ResponseEntity.ok(this.savedTemplateRepository.save(templateToUpdate));
    }

    // DELETE

    @DeleteMapping("/savedTemplates/{id}")
    public ResponseEntity<?> deleteSavedTemplate(@PathVariable("id") Integer id){                                           // TESTED WITH POSTMAN
        Optional<SavedTemplate> templateToDeleteOptional = this.savedTemplateRepository.findBySavedTemplateIdAndArchivedFalse(id);
        if (!templateToDeleteOptional.isPresent()){ //checks if id is valid
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Saved Template Found");
        }
        SavedTemplate templateToDelete = templateToDeleteOptional.get();
        templateToDelete.setArchived(true);
        List<TemplateTask> templateTaskList = this.templateTaskRepository.findByTemplateId(templateToDelete.getSavedTemplateId());
        for (int i = 0; i < templateTaskList.size(); i++) {
            TemplateTask task = templateTaskList.get(i);
            task.setArchived(true);
        }
        this.templateTaskRepository.saveAll(templateTaskList);
        this.savedTemplateRepository.save(templateToDelete);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
