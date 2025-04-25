package com.github.ellisepagong.database;

import com.github.ellisepagong.model.SavedTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedTemplateRepository extends CrudRepository<SavedTemplate, Integer> {

    List<SavedTemplate> findByUserId(int id);

    List<SavedTemplate> findByUserIdAndArchivedFalse(int id);

    List<SavedTemplate> findByUserIdAndSavedTemplateId(Integer id, Integer templateId);

    Optional<SavedTemplate> findBySavedTemplateId(Integer templateId);
}
