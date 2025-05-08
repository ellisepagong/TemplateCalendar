package com.github.ellisepagong.database;

import com.github.ellisepagong.model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer>{

    List<Task> findByUserId(Integer userId);

    List<Task> findByUserIdAndArchivedFalse(Integer userId);

    Optional<Task> findByTaskIdAndArchivedFalse(Integer id);
}
