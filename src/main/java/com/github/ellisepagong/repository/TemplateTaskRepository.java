package com.github.ellisepagong.repository;

import com.github.ellisepagong.model.TemplateTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TemplateTaskRepository extends JpaRepository<TemplateTask, Integer> {

    List<TemplateTask> findBySavedTemplateTaskTemplateId(int id);

    Optional<TemplateTask> findBySavedTemplateTaskId(int id);

    Optional<TemplateTask> findBySavedTemplateTaskIdAndArchivedFalse(int id);

    List<TemplateTask> findBySavedTemplateTaskUserIdAndArchivedFalse(int userId);

    List<TemplateTask> findBySavedTemplateTaskTemplateIdAndArchivedFalse(int templateId);
}
