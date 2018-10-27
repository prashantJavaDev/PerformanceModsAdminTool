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
public class MarketplaceListingSkuDTO {

    private String marketplaceName;
    private String listingId;
    private String marketplaceSku;
    private Date currentListedDate;
    private String currentPrice;
    private String currentQuantity;
    private String warehouseName;

    public Date getCurrentListedDate() {
        return currentListedDate;
    }

    public void setCurrentListedDate(Date currentListedDate) {
        this.currentListedDate = currentListedDate;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(String currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public String getMarketplaceName() {
        return marketplaceName;
    }

    public void setMarketplaceName(String marketplaceName) {
        this.marketplaceName = marketplaceName;
    }

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public String getMarketplaceSku() {
        return marketplaceSku;
    }

    public void setMarketplaceSku(String marketplaceSku) {
        this.marketplaceSku = marketplaceSku;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Override
    public String toString() {
        return "MarketplaceListingSkuDTO{" + "marketplaceName=" + marketplaceName + ", listingId=" + listingId + ", marketplaceSku=" + marketplaceSku + ", currentListedDate=" + currentListedDate + ", currentPrice=" + currentPrice + ", currentQuantity=" + currentQuantity + '}';
    }

}
