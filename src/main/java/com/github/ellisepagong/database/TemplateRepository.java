package com.github.ellisepagong.database;

import com.github.ellisepagong.model.Template;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends CrudRepository<Template, Integer> {

    List<Template> findByUserId(Integer userId);

    List<Template> findByUserIdAndArchivedFalse(int userId);

    List<Template> findByUserIdAndTemplateId(int userId, int templateId);

}
