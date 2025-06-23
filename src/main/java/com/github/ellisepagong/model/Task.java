package com.github.ellisepagong.model;

import jakarta.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;

    @Column(name = "user_id")
    private Integer taskUserId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_description")
    private String taskDesc;

    @Column(name = "assign_date")
    private Date taskDate;

    @Column(name = "is_template")
    private Boolean template;

    @Column(name = "template_id")
    private Integer taskTemplateId;

    @Column(name = "is_saved")
    private Boolean saved;

    @Column(name = "saved_id")
    private Integer taskSavedId;

    @Column(name = "is_archived")
    private Boolean archived;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getTaskUserId() {
        return taskUserId;
    }

    public void setTaskUserId(Integer taskUserId) {
        this.taskUserId = taskUserId;
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

    public Integer getTaskTemplateId() {
        return taskTemplateId;
    }

    public void setTaskTemplateId(Integer taskTemplateId) {
        this.taskTemplateId = taskTemplateId;
    }

    public Boolean getSaved() {
        return saved;
    }

    public void setSaved(Boolean saved) {
        this.saved = saved;
    }

    public Integer getTaskSavedId() {
        return taskSavedId;
    }

    public void setTaskSavedId(Integer taskSavedId) {
        this.taskSavedId = taskSavedId;
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


    @PrePersist
    @PreUpdate
    public void setDefaults(){  // for adding enw entries
        if (this.archived == null){
            this.archived = false;
        }
        if (this.template == null){
            this.template = false;
        }
        if (this.saved == null){
            this.saved = false;
        }
    }

}
