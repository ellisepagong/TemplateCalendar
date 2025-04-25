package com.github.ellisepagong.database;

import com.github.ellisepagong.model.SavedTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class SavedTemplateController  {

    private final SavedTemplateRepository savedTemplateRepository;

    public SavedTemplateController(SavedTemplateRepository savedTemplateRepository) {
        this.savedTemplateRepository = savedTemplateRepository;
    }

    // GET
    @GetMapping("/savedTemplates")
    Iterable<SavedTemplate> getAllSavedTemplates(){
        return this.savedTemplateRepository.findAll();                                                                  // TESTED IN POSTMAN
    }

    @GetMapping("/savedTemplates/ret")
    List<SavedTemplate> searchSavedTemplates(@RequestParam(name = "id") Integer id,
                                                 @RequestParam(name = "templateId", required = false) Integer templateId,
                                                 @RequestParam(name = "archived", required = false) Boolean isArchived){
        if ((id != null) && (id > 0)){ // checks id validity
            if (isArchived != null) {
                return this.savedTemplateRepository.findByUserIdAndArchivedFalse(id);                                   // TESTED IN POSTMAN
            }
            if((templateId != null) && (templateId > 0)){
                return this.savedTemplateRepository.findByUserIdAndSavedTemplateId(id, templateId);                     // TESTED IN POSTMAN
            }
            return this.savedTemplateRepository.findByUserId(id);                                                       // TESTED IN POSTMAN
        }
        return new ArrayList<>();
    }

    // POST

    @PostMapping("/savedTemplates")
    public SavedTemplate createNewSavedTemplate(@RequestBody SavedTemplate savedTemplate){                              // TESTED IN POSTMAN
        SavedTemplate newSavedTemplate = this.savedTemplateRepository.save(savedTemplate); // returns same object but with id
        return newSavedTemplate;
    }

    // PATCH
    @PatchMapping("/savedTemplates/{templateId}")
    public SavedTemplate updateSavedTemplate(@PathVariable("templateId") Integer templateId,                            // TESTED WITH POSTMAN
                                             @RequestBody Map<String, Object> updates) {
        Optional<SavedTemplate> templateToUpdateOptional = this.savedTemplateRepository.findBySavedTemplateId(templateId);
        if (!templateToUpdateOptional.isPresent()) { //checks if id is valid
            return null;
        }

        SavedTemplate templateToUpdate = templateToUpdateOptional.get();

        if (updates.containsKey("templateName")) {
            templateToUpdate.setTemplateName((String) updates.get("templateName"));
        }

        if (updates.containsKey("archived")) {
            templateToUpdate.setArchived((boolean) updates.get("archived"));
        }

        return this.savedTemplateRepository.save(templateToUpdate);
    }

    // DELETE

    @DeleteMapping("/savedTemplates/{id}")
    public SavedTemplate deleteSavedTemplate(@PathVariable("id") Integer id){                                           // TESTED WITH POSTMAN
        Optional<SavedTemplate> templateToDeleteOptional = this.savedTemplateRepository.findById(id);
        if (!templateToDeleteOptional.isPresent()){ //checks if id is valid
            return null;
        }
        SavedTemplate templateToDelete = templateToDeleteOptional.get();
        this.savedTemplateRepository.delete(templateToDelete);
        return templateToDelete;
    }

}
