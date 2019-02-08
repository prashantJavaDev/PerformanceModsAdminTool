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
public class ShippingDTO {

    private int shipping_code;
    private boolean saturday_delivery;
    private boolean signature_required;

    public int getShipping_code() {
        return shipping_code;
    }

    public void setShipping_code(int shipping_code) {
        this.shipping_code = shipping_code;
    }

    public boolean isSaturday_delivery() {
        return saturday_delivery;
    }

    public void setSaturday_delivery(boolean saturday_delivery) {
        this.saturday_delivery = saturday_delivery;
    }

    public boolean isSignature_required() {
        return signature_required;
    }

    public void setSignature_required(boolean signature_required) {
        this.signature_required = signature_required;
    }

    @Override
    public String toString() {
        return "ShippingDTO{" + "shipping_code=" + shipping_code + ", saturday_delivery=" + saturday_delivery + ", signature_required=" + signature_required + '}';
    }
    
}
