package com.github.ellisepagong.model;

import jakarta.persistence.*;

@Entity
@Table(name = "template_task")
public class TemplateTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer templateTaskId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "saved_template_id")
    private Integer templateId;

    @Column(name = "template_task_name")
    private String templateTaskName;

    @Column(name = "template_task_description")
    private String templateTaskDesc;

    @Column(name = "is_archived")
    private Boolean archived;

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public String getTemplateTaskDesc() {
        return templateTaskDesc;
    }

    public void setTemplateTaskDesc(String templateTaskDesc) {
        this.templateTaskDesc = templateTaskDesc;
    }

    public String getTemplateTaskName() {
        return templateTaskName;
    }

    public void setTemplateTaskName(String templateTaskName) {
        this.templateTaskName = templateTaskName;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTemplateTaskId() {
        return templateTaskId;
    }

    public void setTemplateTaskId(Integer templateTaskId) {
        this.templateTaskId = templateTaskId;
    }

    @PrePersist
    @PreUpdate
    public void setDefaults(){
        if (this.archived == null){
            this.archived = false;
        }
    }

}
