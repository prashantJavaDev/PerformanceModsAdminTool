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
public class PriceAttributes {

    private double purchase_cost;
    private boolean has_map;
    List<PriceList> pricelists;

    public double getPurchase_cost() {
        return purchase_cost;
    }

    public void setPurchase_cost(double purchase_cost) {
        this.purchase_cost = purchase_cost;
    }

    public boolean isHas_map() {
        return has_map;
    }

    public void setHas_map(boolean has_map) {
        this.has_map = has_map;
    }

    public List<PriceList> getPricelists() {
        return pricelists;
    }

    public void setPricelists(List<PriceList> pricelists) {
        this.pricelists = pricelists;
    }

    @Override
    public String toString() {
        return "PriceAttributes{" + "purchase_cost=" + purchase_cost + ", has_map=" + has_map + ", pricelists=" + pricelists + '}';
    }

}
