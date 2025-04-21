package com.github.ellisepagong.model;

import jakarta.persistence.*;

@Entity
@Table(name = "saved_template")
public class SavedTemplate {

    @Id
    @GeneratedValue
    private Integer savedTemplateId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "saved_template_name")
    private String templateName;

    @Column(name ="is_archived")
    private Boolean archived;

    public Integer getSavedTemplateId() {
        return savedTemplateId;
    }

    public void setSavedTemplateId(Integer savedTemplateId) {
        this.savedTemplateId = savedTemplateId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }
}
