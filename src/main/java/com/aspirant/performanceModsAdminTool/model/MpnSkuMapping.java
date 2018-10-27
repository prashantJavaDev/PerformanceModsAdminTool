/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.model;

/**
 *
 * @author Ritesh
 */
public class MpnSkuMapping {
    
    private String manufacturerMPN;

    public String getManufacturerMPN() {
        return manufacturerMPN;
    }

    public void setManufacturerMPN(String manufacturerMPN) {
        this.manufacturerMPN = manufacturerMPN;
    }

    @Override
    public String toString() {
        return "MpnSkuMapping{" + "manufacturerMPN=" + manufacturerMPN + '}';
    }

     
    
}
