package com.github.ellisepagong.model;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue
    private Integer task_id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_description")
    private String taskDesc;

    @Column(name = "assign_date")
    private Date taskDate;

    @Column(name = "is_template")
    private Boolean isTemplate;

    @Column(name = "template_id", nullable = true)
    private Integer templateID;

    @Column(name = "is_saved")
    private Boolean isSaved;

    @Column(name = "saved_id", nullable = true)
    private Integer saved_id;

    @Column(name = "is_archived")
    private Boolean archived;


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

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public Boolean getTemplate() {
        return isTemplate;
    }

    public void setTemplate(Boolean template) {
        isTemplate = template;
    }

    public Integer getTemplateID() {
        return templateID;
    }

    public void setTemplateID(Integer templateID) {
        this.templateID = templateID;
    }

    public Boolean isSaved() {
        return isSaved;
    }

    public void setSaved(Boolean saved) {
        isSaved = saved;
    }

    public Integer getSaved_id() {
        return saved_id;
    }

    public void setSaved_id(Integer saved_id) {
        this.saved_id = saved_id;
    }

    public Boolean isArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }
}
