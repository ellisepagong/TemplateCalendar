package com.github.ellisepagong.repository;

import com.github.ellisepagong.model.SavedTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedTemplateRepository extends JpaRepository<SavedTemplate, Integer> {

    List<SavedTemplate> findBySavedTemplateUserIdAndArchivedFalse(int id);

    List<SavedTemplate> findBySavedTemplateUserIdAndSavedTemplateId(Integer id, Integer templateId);

    Optional<SavedTemplate> findBySavedTemplateIdAndArchivedFalse(Integer templateId);
}
