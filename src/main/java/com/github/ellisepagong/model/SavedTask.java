package com.github.ellisepagong.model;

import jakarta.persistence.*;

@Entity
@Table(name = "saved_task")
public class SavedTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer savedTaskId;

    @Column(name = "user_id")
    private Integer savedTaskUserId;

    @Column(name = "saved_task_name")
    private String savedTaskName;

    @Column(name = "saved_task_description")
    private String savedTaskDesc;

    @Column(name = "is_archived")
    private Boolean archived;


    public Integer getSavedTaskId() {
        return savedTaskId;
    }

    public void setSavedTaskId(Integer savedTaskId) {
        this.savedTaskId = savedTaskId;
    }

    public Integer getSavedTaskUserId() {
        return savedTaskUserId;
    }

    public void setSavedTaskUserId(Integer savedTaskUserId) {
        this.savedTaskUserId = savedTaskUserId;
    }

    public String getSavedTaskName() {
        return savedTaskName;
    }

    public void setSavedTaskName(String savedTaskName) {
        this.savedTaskName = savedTaskName;
    }

    public String getSavedTaskDesc() {
        return savedTaskDesc;
    }

    public void setSavedTaskDesc(String savedTaskDesc) {
        this.savedTaskDesc = savedTaskDesc;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    @PrePersist
    @PreUpdate
    public void setDefaults(){
        if (this.archived == null){
            this.archived = false;
        }
    }


}
