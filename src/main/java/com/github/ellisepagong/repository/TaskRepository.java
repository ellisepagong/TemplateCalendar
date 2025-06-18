package com.github.ellisepagong.repository;

import com.github.ellisepagong.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByTaskUserIdAndArchivedFalse(Integer userId);

    Optional<Task> findByTaskIdAndArchivedFalse(Integer id);

    List<Task> findByTaskTemplateIdAndArchivedFalse(Integer templateId);
}
