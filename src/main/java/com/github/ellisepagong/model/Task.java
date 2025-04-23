package com.github.ellisepagong.model;

import jakarta.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Boolean template;

    @Column(name = "template_id")
    private Integer templateId;

    @Column(name = "is_saved")
    private Boolean saved;

    @Column(name = "saved_id")
    private Integer savedId;

    @Column(name = "is_archived")
    private Boolean archived;

    public Integer getTask_id() {
        return task_id;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
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
        return template;
    }

    public void setTemplate(Boolean template) {
        this.template = template;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Boolean getSaved() {
        return saved;
    }

    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    public Integer getSavedId() {
        return savedId;
    }

    public void setSavedId(Integer savedId) {
        this.savedId = savedId;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public boolean isDateValid(){ // checks if date is before current date or not
       return taskDate != null && !(this.taskDate.before(Date.valueOf(LocalDate.now())));
    }

}
