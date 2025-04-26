package com.github.ellisepagong.database;

import com.github.ellisepagong.model.TemplateTask;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TemplateTaskRepository extends CrudRepository<TemplateTask, Integer> {

    List<TemplateTask> findByUserId(int id);

    List<TemplateTask> findByTemplateId(int id);

    Optional<TemplateTask> findByTemplateTaskId(int id);

}
