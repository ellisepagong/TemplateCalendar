package com.github.ellisepagong.database;

import com.github.ellisepagong.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer>{

    List<Task> findByUserId(Integer userId);

    List<Task> findByUserIdAndIsArchivedFalse(int userId);

    List<Task> findByUserIdAndTemplateIdAndIsArchivedFalse(Integer userId, Integer templateId);
}
