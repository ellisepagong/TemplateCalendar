package com.github.ellisepagong.controller;

import com.github.ellisepagong.repository.SavedTemplateRepository;
import com.github.ellisepagong.repository.TemplateTaskRepository;
import com.github.ellisepagong.model.SavedTemplate;
import com.github.ellisepagong.model.TemplateTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/savedTemplates")
public class SavedTemplateController {

    private final SavedTemplateRepository savedTemplateRepository;
    private final TemplateTaskRepository templateTaskRepository;

    public SavedTemplateController(SavedTemplateRepository savedTemplateRepository, TemplateTaskRepository templateTaskRepository) {
        this.savedTemplateRepository = savedTemplateRepository;
        this.templateTaskRepository = templateTaskRepository;
    }

    // GET

    @GetMapping("/{id}")
    ResponseEntity<?> searchSavedTemplate(@PathVariable("id") Integer savedTemplateId) {
        Optional<SavedTemplate> savedTemplateOptional = this.savedTemplateRepository.findBySavedTemplateIdAndArchivedFalse(savedTemplateId);
        if (savedTemplateOptional.isPresent()) {
            return ResponseEntity.ok(savedTemplateOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Saved Template Found");
        }
    }

    @GetMapping("/")
    ResponseEntity<?> searchSavedTemplates(@RequestParam(name = "id", required = false) Integer id) {
        if (id != null) {
            List<SavedTemplate> savedTemplateList = this.savedTemplateRepository.findBySavedTemplateUserIdAndArchivedFalse(id);

            if (savedTemplateList.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Saved Templates Found");
            }else{
                return ResponseEntity.ok(savedTemplateList);
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Arguments");
        }
    }

    // POST

    @PostMapping("")
    public ResponseEntity<?> createNewSavedTemplate(@RequestBody SavedTemplate savedTemplate) {
        SavedTemplate newSavedTemplate = this.savedTemplateRepository.save(savedTemplate);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSavedTemplate);
    }

    // PATCH
    @PatchMapping("/{templateId}")
    public ResponseEntity<?> updateSavedTemplate(@PathVariable("templateId") Integer templateId,
                                                 @RequestBody Map<String, Object> updates) {
        Optional<SavedTemplate> templateToUpdateOptional = this.savedTemplateRepository.findBySavedTemplateIdAndArchivedFalse(templateId);
        if (!templateToUpdateOptional.isPresent()) { //checks if id is valid
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Saved Template Found");
        }
        SavedTemplate templateToUpdate = templateToUpdateOptional.get();

        if (updates.containsKey("templateName")) {
            templateToUpdate.setSavedTemplateName((String) updates.get("templateName"));
        }

        return ResponseEntity.ok(this.savedTemplateRepository.save(templateToUpdate));
    }

    // DELETE

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSavedTemplate(@PathVariable("id") Integer id) {
        Optional<SavedTemplate> templateToDeleteOptional = this.savedTemplateRepository.findBySavedTemplateIdAndArchivedFalse(id);
        if (!templateToDeleteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Saved Template Found");
        }
        SavedTemplate templateToDelete = templateToDeleteOptional.get();
        templateToDelete.setArchived(true);
        List<TemplateTask> templateTaskList = this.templateTaskRepository.findBySavedTemplateTaskTemplateId(templateToDelete.getSavedTemplateId());
        for (int i = 0; i < templateTaskList.size(); i++) {
            TemplateTask task = templateTaskList.get(i);
            task.setArchived(true);
        }
        this.templateTaskRepository.saveAll(templateTaskList);
        this.savedTemplateRepository.save(templateToDelete);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
