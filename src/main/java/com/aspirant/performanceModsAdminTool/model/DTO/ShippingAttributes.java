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
public class ShippingAttributes {

    private String transportation_name;
    private String carrier_name;

    public String getTransportation_name() {
        return transportation_name;
    }

    public void setTransportation_name(String transportation_name) {
        this.transportation_name = transportation_name;
    }

    public String getCarrier_name() {
        return carrier_name;
    }

    public void setCarrier_name(String carrier_name) {
        this.carrier_name = carrier_name;
    }

    @Override
    public String toString() {
        return "ShippingAttributes{" + "transportation_name=" + transportation_name + ", carrier_name=" + carrier_name + '}';
    }
    
}
