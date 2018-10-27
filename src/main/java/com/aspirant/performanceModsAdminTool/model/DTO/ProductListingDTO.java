/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.DTO;

import java.util.Date;

/**
 *
 * @author Ritesh
 */
public class ProductListingDTO {

    private String sku;
    private String listingId;
    private double listedPrice;
    private int listedQuantity;
    private double currentPrice;
    private int currentQuantity;
    private String warehouse;
    private boolean Active;
    private int warehouseId;
    private Date lastListedDate;
    private Date currentListedDate;
    private String manufacturerMpn;
    private String manufacturerName;
    private double shipping;
    private double commission;
    private double profit;
    private int profitPercentage;
    private int commissionPercentage;
    private int warehouseQuantity;
    private double warehousePrice;
    private int pack;

    public int getCommissionPercentage() {
        return commissionPercentage;
    }

    public void setCommissionPercentage(int commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public double getListedPrice() {
        return listedPrice;
    }

    public void setListedPrice(double listedPrice) {
        this.listedPrice = listedPrice;
    }

    public int getListedQuantity() {
        return listedQuantity;
    }

    public void setListedQuantity(int listedQuantity) {
        this.listedQuantity = listedQuantity;
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

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean Active) {
        this.Active = Active;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Date getLastListedDate() {
        return lastListedDate;
    }

    public void setLastListedDate(Date lastListedDate) {
        this.lastListedDate = lastListedDate;
    }

    public Date getCurrentListedDate() {
        return currentListedDate;
    }

    public void setCurrentListedDate(Date currentListedDate) {
        this.currentListedDate = currentListedDate;
    }

    public String getManufacturerMpn() {
        return manufacturerMpn;
    }

    public void setManufacturerMpn(String manufacturerMpn) {
        this.manufacturerMpn = manufacturerMpn;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public int getProfitPercentage() {
        return profitPercentage;
    }

    public void setProfitPercentage(int profitPercentage) {
        this.profitPercentage = profitPercentage;
    }

    public int getWarehouseQuantity() {
        return warehouseQuantity;
    }

    public void setWarehouseQuantity(int warehouseQuantity) {
        this.warehouseQuantity = warehouseQuantity;
    }

    public double getWarehousePrice() {
        return warehousePrice;
    }

    public void setWarehousePrice(double warehousePrice) {
        this.warehousePrice = warehousePrice;
    }

    public int getPack() {
        return pack;
    }

    public void setPack(int pack) {
        this.pack = pack;
    }

}
