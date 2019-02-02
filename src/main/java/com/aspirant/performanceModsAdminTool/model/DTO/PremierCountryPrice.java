/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.DTO;

/**
 *
 * @author pk
 */
public class PremierCountryPrice {

    private float cost;
    private float jobber;
    private float retail;
    private float map;
    private String currency;

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getJobber() {
        return jobber;
    }

    public void setJobber(float jobber) {
        this.jobber = jobber;
    }

    public float getRetail() {
        return retail;
    }

    public void setRetail(float retail) {
        this.retail = retail;
    }

    public float getMap() {
        return map;
    }

    public void setMap(float map) {
        this.map = map;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "PremierCountryPrice{" + "cost=" + cost + ", jobber=" + jobber + ", retail=" + retail + ", map=" + map + ", currency=" + currency + '}';
    }
    
}
