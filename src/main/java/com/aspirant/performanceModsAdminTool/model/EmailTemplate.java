/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ritesh
 */
public class EmailTemplate implements Serializable {

    private int emailTemplateId;
    private String subject;
    private String subjectParam;
    private String body;
    private String bodyParam;
    private User createdBy;
    private Date createdDate;
    private User lastModifiedBy;
    private Date lastModifiedDate;
    private boolean active;

    public int getEmailTemplateId() {
        return emailTemplateId;
    }

    public void setEmailTemplateId(int emailTemplateId) {
        this.emailTemplateId = emailTemplateId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubjectParam() {
        return subjectParam;
    }

    public void setSubjectParam(String subjectParam) {
        this.subjectParam = subjectParam;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBodyParam() {
        return bodyParam;
    }

    public void setBodyParam(String bodyParam) {
        this.bodyParam = bodyParam;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
