/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model;

import java.util.Date;

/**
 *
 * @author ubuntu
 */
public class EmailerBlock {
    
    private int emailerBlockId;
    private String emailerBlockName;
    private String emailerBlockDesc;
    private Date createdDate;
    private User createdBy;
    private Date lastModifiedDate;
    private User lastModifiedBy;
    private boolean active;

    public int getEmailerBlockId() {
        return emailerBlockId;
    }

    public void setEmailerBlockId(int emailerBlockId) {
        this.emailerBlockId = emailerBlockId;
    }

    public String getEmailerBlockName() {
        return emailerBlockName;
    }

    public void setEmailerBlockName(String emailerBlockName) {
        this.emailerBlockName = emailerBlockName;
    }

    public String getEmailerBlockDesc() {
        return emailerBlockDesc;
    }

    public void setEmailerBlockDesc(String emailerBlockDesc) {
        this.emailerBlockDesc = emailerBlockDesc;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "EmailerBlock{" + "emailerBlockId=" + emailerBlockId + ", emailerBlockName=" + emailerBlockName + ", emailerBlockDesc=" + emailerBlockDesc + ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedBy=" + lastModifiedBy + ", active=" + active + '}';
    }
    
    
    
}
