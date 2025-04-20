package com.github.ellisepagong.database;

import com.github.ellisepagong.model.Task;
import com.github.ellisepagong.model.Template;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TemplateController {

    private final TemplateRepository templateRepository;

    public TemplateController(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }


    // GET
    @GetMapping("/templates")
    public Iterable<Template> getAllTemplates(){
        return this.templateRepository.findAll();
    }

    @GetMapping("/templates/ret")
    public List<Template> searchTemplates(@RequestParam(name = "userId") Integer id,
                                 @RequestParam(name ="notArchived", required = false) Boolean notArchived){
        // TODO
        return new ArrayList<>();

    }

    // POST
    @PostMapping("/templates") //TODO: test
    public Template createNewTemplate(@RequestBody Template template){
        Template newTemplate = this.templateRepository.save(template); // returns same object but with id
        return newTemplate;
    }

    // PUT
    @PutMapping("/templates/{id}") //TODO: test
    public Template updateTemplate(@PathVariable("id") Integer id, @RequestBody Template t) {
        Optional<Template> templateToUpdateOptional = this.templateRepository.findById(id);
        if (!templateToUpdateOptional.isPresent()) { //checks if id is valid
            return null;
        }

        Template templateToUpdate = templateToUpdateOptional.get();
        // TODO add setters
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
