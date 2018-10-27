/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.model;

/**
 *
 * @author Pallavi
 */
public class CurrentWarehouseProduct {
    
    private double price;
    private double shipping;
    private String warehouseIdentificationNum;

    public String getWarehouseIdentificationNum() {
        return warehouseIdentificationNum;
    }

    public void setWarehouseIdentificationNum(String warehouseIdentificationNum) {
        this.warehouseIdentificationNum = warehouseIdentificationNum;
    }
    

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    @Override
    public String toString() {
        return "CurrentWarehouseProduct{" + "price=" + price + ", shipping=" + shipping + ", warehouseIdentificationNum=" + warehouseIdentificationNum + '}';
    }
    
    
    
}
