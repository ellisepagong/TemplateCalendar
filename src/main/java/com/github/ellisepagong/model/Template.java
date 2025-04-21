package com.github.ellisepagong.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue
    private Integer templateId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "saved_template_id")
    private Integer savedId;

    @Column(name = "template_name")
    private String name;

    @Column(name = "assign_date")
    private Date date;

    @Column(name = "is_archived")
    private Boolean archived;

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

    public Integer getSavedId() {
        return savedId;
    }

    public void setSavedId(Integer savedId) {
        this.savedId = savedId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }
}
