/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.DTO;

import java.util.Date;

/**
 *
 * @author pk
 */
public class ManufacturerAPIDTO {

    private String stock;

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

   
    @Override
    public String toString() {
        return "ManufacturerAPIDTO{" + "stock=" + stock + '}';
    }
    
    
}
