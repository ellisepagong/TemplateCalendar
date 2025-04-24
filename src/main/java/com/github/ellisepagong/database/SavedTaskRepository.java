package com.github.ellisepagong.database;

import com.github.ellisepagong.model.SavedTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedTaskRepository extends CrudRepository<SavedTask, Integer> {

    List<SavedTask> findByUserId(Integer userId);

    List<SavedTask> findByUserIdAndArchivedFalse(Integer userId);

    Optional<SavedTask> findBySavedTaskId(Integer SavedTaskId);
}
