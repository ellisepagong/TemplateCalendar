package com.github.ellisepagong.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer templateId;

    @Column(name = "user_id")
    private Integer templateUserId;

    @Column(name = "saved_template_id")
    private Integer templateSavedId;

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "assign_date")
    private Date templateDate;

    @Column(name = "is_archived")
    private Boolean archived;

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getTemplateUserId() {
        return templateUserId;
    }

    public void setTemplateUserId(Integer templateUserId) {
        this.templateUserId = templateUserId;
    }

    public Integer getTemplateSavedId() {
        return templateSavedId;
    }

    public void setSavedTemplateId(Integer savedId) {
        this.templateSavedId = savedId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Date getTemplateDate() {
        return templateDate;
    }

    public void setTemplateDate(Date templateDate) {
        this.templateDate = templateDate;
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
        if (this.archived == null) {
            this.archived = false;
        }
    }
}
