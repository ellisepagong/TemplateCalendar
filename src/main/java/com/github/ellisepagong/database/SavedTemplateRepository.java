package com.github.ellisepagong.database;

import com.github.ellisepagong.model.SavedTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedTemplateRepository extends CrudRepository<SavedTemplate, Integer> {

    List<SavedTemplate> findByUserId(int id);

    List<SavedTemplate> findByUserIdAndArchivedFalse(int id);

    List<SavedTemplate> findByUserIdAndSavedTemplateIdAndArchivedFalse(Integer id, Integer templateId);
}
