package com.github.ellisepagong.database;

import com.github.ellisepagong.model.SavedTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
        return this.savedTemplateRepository.findAll(); // todo: test
    }

    @GetMapping("/savedTemplates/ret")
    List<SavedTemplate> searchSavedTemplates(@RequestParam(name = "id") Integer id,
                                                 @RequestParam(name = "templateId", required = false) Integer templateId,
                                                 @RequestParam(name = "archived", required = false) Boolean isArchived){
        if ((id != null) && (id > 0)){ // checks id validity
            if (isArchived != null) {
                if((templateId != null) && (templateId > 0)){ // for selecting tasks associated with a template
                    return this.savedTemplateRepository.findByUserIdAndSavedTemplateIdAndArchivedFalse(id, templateId); //TODO: test
                }
                return this.savedTemplateRepository.findByUserIdAndArchivedFalse(id); // todo: test
            }
            return this.savedTemplateRepository.findByUserId(id); // todo test
        }
        return new ArrayList<>();
    }

    // POST

    @PostMapping("/savedTemplates") //TODO: test
    public SavedTemplate createNewSavedTemplate(@RequestBody SavedTemplate savedTemplate){
        SavedTemplate newSavedTemplate = this.savedTemplateRepository.save(savedTemplate); // returns same object but with id
        return newSavedTemplate;
    }

    // PUT

    @PutMapping("/savedTemplates/{id}") //TODO: test
    public SavedTemplate updateSavedTemplate(@PathVariable("id") Integer id, @RequestBody SavedTemplate t) {
        Optional<SavedTemplate> templateToUpdateOptional = this.savedTemplateRepository.findById(id);
        if (!templateToUpdateOptional.isPresent()) { //checks if id is valid
            return null;
        }

        SavedTemplate templateToUpdate = templateToUpdateOptional.get();

        if (t.getUserId() != null) {
            templateToUpdate.setUserId(t.getUserId());
        }

        if (t.getTemplateName()!= null) {
            templateToUpdate.setTemplateName(t.getTemplateName());
        }

        if (t.getSavedTemplateId() != null) {
            templateToUpdate.setSavedTemplateId(t.getSavedTemplateId());
        }

        if (t.getArchived() != null) {
            templateToUpdate.setArchived(t.getArchived());
        }

        return templateToUpdate;
    }

    // DELETE

    @DeleteMapping("/savedTemplates/{id}") // TODO: test
    public SavedTemplate deleteSavedTemplate(@PathVariable("id") Integer id){
        Optional<SavedTemplate> templateToDeleteOptional = this.savedTemplateRepository.findById(id);
        if (!templateToDeleteOptional.isPresent()){ //checks if id is valid
            return null;
        }
        SavedTemplate templateToDelete = templateToDeleteOptional.get();
        this.savedTemplateRepository.delete(templateToDelete);
        return templateToDelete;
    }

}
