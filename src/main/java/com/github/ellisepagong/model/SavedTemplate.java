package com.github.ellisepagong.model;

import jakarta.persistence.*;

@Entity
@Table(name = "saved_template")
public class SavedTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer savedTemplateId;

    @Column(name = "user_id")
    private Integer savedTemplateUserId;

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

    public Integer getSavedTemplateUserId() {
        return savedTemplateUserId;
    }

    public void setSavedTemplateUserId(Integer savedTemplateUserId) {
        this.savedTemplateUserId = savedTemplateUserId;
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

    @PrePersist
    @PreUpdate
    public void setDefaults(){
        if (this.archived == null) {
            this.archived = false;
        }
    }
}
