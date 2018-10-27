/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Pallavi
 */
public class ChildOfChildCategory  implements Serializable{
    
    private int subChildCategoryId;
    private ChildOfSubCategory childOfSubCategory;
    private String subChildCategoryDesc;
    private Date createdDate;
    private User createdBy;
    private Date lastModifiedDate;
    private User lastModifiedBy;
    private boolean active;

    public int getSubChildCategoryId() {
        return subChildCategoryId;
    }

    public void setSubChildCategoryId(int subChildCategoryId) {
        this.subChildCategoryId = subChildCategoryId;
    }


    public String getSubChildCategoryDesc() {
        return subChildCategoryDesc;
    }

    public void setSubChildCategoryDesc(String subChildCategoryDesc) {
        this.subChildCategoryDesc = subChildCategoryDesc;
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

    public ChildOfSubCategory getChildOfSubCategory() {
        return childOfSubCategory;
    }

    public void setChildOfSubCategory(ChildOfSubCategory childOfSubCategory) {
        this.childOfSubCategory = childOfSubCategory;
    }

    @Override
    public String toString() {
        return "ChildOfChildCategory{" + "subChildCategoryId=" + subChildCategoryId + ", childOfSubCategory=" + childOfSubCategory + ", subChildCategoryDesc=" + subChildCategoryDesc + ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedBy=" + lastModifiedBy + ", active=" + active + '}';
    }

    
}
