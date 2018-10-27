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
public class ChildOfSubCategory  implements Serializable{
    
    private int childCategoryId;
    private SubCategory subCategory;
    private String childCategoryDesc;
    private Date createdDate;
    private User createdBy;
    private Date lastModifiedDate;
    private User lastModifiedBy;
    private boolean active;
    private int[] subChildCategoryId;

    public int getChildCategoryId() {
        return childCategoryId;
    }

    public void setChildCategoryId(int childCategoryId) {
        this.childCategoryId = childCategoryId;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public String getChildCategoryDesc() {
        return childCategoryDesc;
    }

    public void setChildCategoryDesc(String childCategoryDesc) {
        this.childCategoryDesc = childCategoryDesc;
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

    public int[] getSubChildCategoryId() {
        return subChildCategoryId;
    }

    public void setSubChildCategoryId(int[] subChildCategoryId) {
        this.subChildCategoryId = subChildCategoryId;
    }

    @Override
    public String toString() {
        return "ChildOfSubCategory{" + "childCategoryId=" + childCategoryId + ", subCategory=" + subCategory + ", childCategoryDesc=" + childCategoryDesc + ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedBy=" + lastModifiedBy + ", active=" + active + ", subChildCategoryId=" + subChildCategoryId + '}';
    }

}
