package com.github.ellisepagong.model;

import jakarta.persistence.*;

@Entity
@Table(name = "saved_task")
public class SavedTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer savedTaskId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "saved_task_name")
    private String taskName;

    @Column(name = "saved_task_description")
    private String taskDescription;

    @Column(name = "is_archived")
    private Boolean archived;


    public Integer getSavedTaskId() {
        return savedTaskId;
    }

    public void setSavedTaskId(Integer savedTaskId) {
        this.savedTaskId = savedTaskId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }
}
