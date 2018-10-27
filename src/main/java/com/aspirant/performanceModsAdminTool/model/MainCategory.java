/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author shrutika
 */
public class MainCategory implements Serializable {

    private int mainCategoryId;
    private String mainCategoryDesc;
    private Date createdDate;
    private User createdBy;
    private Date lastModifiedDate;
    private User lastModifiedBy;
    private boolean active;
    private int[] subCategoryId;

    public int getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(int mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

    public String getMainCategoryDesc() {
        return mainCategoryDesc;
    }

    public void setMainCategoryDesc(String mainCategoryDesc) {
        this.mainCategoryDesc = mainCategoryDesc;
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

    public int[] getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int[] subCategoryId) {
        this.subCategoryId = subCategoryId;
    }
}
