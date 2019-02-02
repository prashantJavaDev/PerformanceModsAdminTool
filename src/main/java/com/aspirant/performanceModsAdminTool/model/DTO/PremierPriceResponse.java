/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.DTO;

import java.util.List;

/**
 *
 * @author pk
 */
public class PremierPriceResponse {

    private int id;
    private String itemNumber;
    private List<PremierCountryPrice> pricing;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public List<PremierCountryPrice> getPricing() {
        return pricing;
    }

    public void setPricing(List<PremierCountryPrice> pricing) {
        this.pricing = pricing;
    }

    @Override
    public String toString() {
        return "PremierPriceResponse{" + "id=" + id + ", itemNumber=" + itemNumber + ", pricing=" + pricing + '}';
    }

}
