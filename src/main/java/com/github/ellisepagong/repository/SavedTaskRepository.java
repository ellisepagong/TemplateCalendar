package com.github.ellisepagong.repository;

import com.github.ellisepagong.model.SavedTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedTaskRepository extends JpaRepository<SavedTask, Integer> {

    List<SavedTask> findBySavedTaskUserIdAndArchivedFalse(Integer userId);

    Optional<SavedTask> findBySavedTaskIdAndArchivedFalse(Integer savedTaskId);

    Optional<SavedTask> findBySavedTaskId(Integer SavedTaskId);
}
