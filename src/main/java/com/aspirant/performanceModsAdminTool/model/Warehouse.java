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
public class Warehouse implements Serializable {

    private String warehouseId;
    private String warehouseName;
    private String warehouseAddress;
    private String warehousePhone;
    private String warehouseRepName;
    private String warehouseRepEmail;
    private String warehouseCustServiceEmail;
    private Date createdDate;
    private User createdBy;
    private Date lastModifiedDate;
    private User lastModifiedBy;
    private boolean active;

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
    }

    public String getWarehousePhone() {
        return warehousePhone;
    }

    public void setWarehousePhone(String warehousePhone) {
        this.warehousePhone = warehousePhone;
    }

    public String getWarehouseRepName() {
        return warehouseRepName;
    }

    public void setWarehouseRepName(String warehouseRepName) {
        this.warehouseRepName = warehouseRepName;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
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

    public String getWarehouseRepEmail() {
        return warehouseRepEmail;
    }

    public void setWarehouseRepEmail(String warehouseRepEmail) {
        this.warehouseRepEmail = warehouseRepEmail;
    }

    public String getWarehouseCustServiceEmail() {
        return warehouseCustServiceEmail;
    }

    public void setWarehouseCustServiceEmail(String warehouseCustServiceEmail) {
        this.warehouseCustServiceEmail = warehouseCustServiceEmail;
    }

    @Override
    public String toString() {
        return "Warehouse{" + "warehouseId=" + warehouseId + ", warehouseName=" + warehouseName + ", warehouseAddress=" + warehouseAddress + ", warehousePhone=" + warehousePhone + ", warehouseRepName=" + warehouseRepName + ", warehouseRepEmail=" + warehouseRepEmail + ", warehouseCustServiceEmail=" + warehouseCustServiceEmail + ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", lastModifiedDate=" + lastModifiedDate + ", lastModifiedBy=" + lastModifiedBy + ", active=" + active + '}';
    }
    
}
