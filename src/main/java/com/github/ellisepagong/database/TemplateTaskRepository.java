package com.github.ellisepagong.database;

import com.github.ellisepagong.model.TemplateTask;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TemplateTaskRepository extends CrudRepository<TemplateTask, Integer> {

    List<TemplateTask> findBySavedTemplateTaskTemplateId(int id);

    Optional<TemplateTask> findBySavedTemplateTaskId(int id);

    Optional<TemplateTask> findBySavedTemplateTaskIdAndArchivedFalse(int id);

    List<TemplateTask> findBySavedTemplateTaskUserIdAndArchivedFalse(int userId);

    List<TemplateTask> findBySavedTemplateTaskTemplateIdAndArchivedFalse(int templateId);
}
