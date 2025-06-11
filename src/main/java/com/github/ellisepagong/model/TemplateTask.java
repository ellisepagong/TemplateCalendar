package com.github.ellisepagong.model;

import jakarta.persistence.*;

@Entity
@Table(name = "template_task")
public class TemplateTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer savedTemplateTaskId;

    @Column(name = "user_id")
    private Integer savedTemplateTaskUserId;

    @Column(name = "saved_template_id")
    private Integer savedTemplateTaskTemplateId;

    @Column(name = "template_task_name")
    private String savedTemplateTaskName;

    @Column(name = "template_task_description")
    private String savedTemplateTaskDesc;

    @Column(name = "is_archived")
    private Boolean archived;

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public String getSavedTemplateTaskDesc() {
        return savedTemplateTaskDesc;
    }

    public void setSavedTemplateTaskDesc(String savedTemplateTaskDesc) {
        this.savedTemplateTaskDesc = savedTemplateTaskDesc;
    }

    public String getSavedTemplateTaskName() {
        return savedTemplateTaskName;
    }

    public void setSavedTemplateTaskName(String savedTemplateTaskName) {
        this.savedTemplateTaskName = savedTemplateTaskName;
    }

    public Integer getSavedTemplateTaskTemplateId() {
        return savedTemplateTaskTemplateId;
    }

    public void setSavedTemplateTaskTemplateId(Integer savedTemplateTaskTemplateId) {
        this.savedTemplateTaskTemplateId = savedTemplateTaskTemplateId;
    }

    public Integer getSavedTemplateTaskUserId() {
        return savedTemplateTaskUserId;
    }

    public void setSavedTemplateTaskUserId(Integer savedTemplateTaskUserId) {
        this.savedTemplateTaskUserId = savedTemplateTaskUserId;
    }

    public Integer getSavedTemplateTaskId() {
        return savedTemplateTaskId;
    }

    public void setSavedTemplateTaskId(Integer savedTemplateTaskId) {
        this.savedTemplateTaskId = savedTemplateTaskId;
    }

    @PrePersist
    @PreUpdate
    public void setDefaults(){
        if (this.archived == null){
            this.archived = false;
        }
    }

}
