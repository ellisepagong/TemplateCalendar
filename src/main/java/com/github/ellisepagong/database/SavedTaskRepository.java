package com.github.ellisepagong.database;

import com.github.ellisepagong.model.SavedTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedTaskRepository extends CrudRepository<SavedTask, Integer> {

    List<SavedTask> findBySavedTaskUserIdAndArchivedFalse(Integer userId);

    Optional<SavedTask> findBySavedTaskIdAndArchivedFalse(Integer savedTaskId);

    Optional<SavedTask> findBySavedTaskId(Integer SavedTaskId);
}
