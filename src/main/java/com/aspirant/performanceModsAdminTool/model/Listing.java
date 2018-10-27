/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model;

import java.io.Serializable;

/**
 *
 * @author Ritesh
 */
public class Listing implements Serializable {

    private String productMpn;
    private String marketplaceListingId;
    private Marketplace marketplace;
    private double commission;
    private int pack;
    private String sku;
    private Manufacturer manufacturer;
    private double lastListedPrice;
    private boolean isAmazonFulfilled;
    private int currentQunatity;
    private double currentPrice;

    public double getLastListedPrice() {
        return lastListedPrice;
    }

    public void setLastListedPrice(double lastListedPrice) {
        this.lastListedPrice = lastListedPrice;
    }

    public boolean isIsAmazonFulfilled() {
        return isAmazonFulfilled;
    }

    public void setIsAmazonFulfilled(boolean isAmazonFulfilled) {
        this.isAmazonFulfilled = isAmazonFulfilled;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMarketplaceListingId() {
        return marketplaceListingId;
    }

    public void setMarketplaceListingId(String marketplaceListingId) {
        this.marketplaceListingId = marketplaceListingId;
    }

    public String getProductMpn() {
        return productMpn;
    }

    public void setProductMpn(String productMpn) {
        this.productMpn = productMpn;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public int getPack() {
        return pack;
    }

    public void setPack(int pack) {
        this.pack = pack;
    }

    public int getCurrentQunatity() {
        return currentQunatity;
    }

    public void setCurrentQunatity(int currentQunatity) {
        this.currentQunatity = currentQunatity;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return "Listing{" + "productMpn=" + productMpn + ", marketplaceListingId=" + marketplaceListingId + ", marketplace=" + marketplace + ", commission=" + commission + ", pack=" + pack + ", sku=" + sku + ", manufacturer=" + manufacturer + ", lastListedPrice=" + lastListedPrice + ", isAmazonFulfilled=" + isAmazonFulfilled + ", currentQunatity=" + currentQunatity + ", currentPrice=" + currentPrice + '}';
    }

    
 
    

    
     

     
}
