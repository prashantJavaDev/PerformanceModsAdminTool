/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model;

import java.io.Serializable;

/**
 *
 * @author shrutika
 */
public class ProductDetails implements Serializable {

    private int warehouseId;
    private String warehouseName;
    private String currentFeedDate;
    private double currentPrice;
    private double shipping;
    private int currentQuantity;
    private String warehouseMpn;
    private String imageUrl;
    private Order processedBy;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseMpn() {
        return warehouseMpn;
    }

    public void setWarehouseMpn(String warehouseMpn) {
        this.warehouseMpn = warehouseMpn;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getCurrentFeedDate() {
        return currentFeedDate;
    }

    public void setCurrentFeedDate(String currentFeedDate) {
        this.currentFeedDate = currentFeedDate;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public Order getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Order processedBy) {
        this.processedBy = processedBy;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }
    
    

    @Override
    public String toString() {
        return "ProductDetails{" + "warehouseId=" + warehouseId + ", warehouseName=" + warehouseName + ", currentFeedDate=" + currentFeedDate + ", currentPrice=" + currentPrice + ", currentQuantity=" + currentQuantity + ", warehouseMpn=" + warehouseMpn + ", imageUrl=" + imageUrl + ", processedBy=" + processedBy + '}';
    }
    
    

}
