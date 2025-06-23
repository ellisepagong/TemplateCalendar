package com.github.ellisepagong.database;

import com.github.ellisepagong.model.Template;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TemplateRepository extends CrudRepository<Template, Integer> {

    List<Template> findByTemplateUserIdAndArchivedFalse(int userId);

    Optional<Template> findByTemplateIdAndArchivedFalse(int templateId);

}
