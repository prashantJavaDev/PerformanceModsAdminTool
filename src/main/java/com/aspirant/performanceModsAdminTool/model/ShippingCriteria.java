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
public class ShippingCriteria implements Serializable{
    
    private int shippingCriteriaId;
    private String shippingCriteria;

    public int getShippingCriteriaId() {
        return shippingCriteriaId;
    }

    public void setShippingCriteriaId(int shippingCriteriaId) {
        this.shippingCriteriaId = shippingCriteriaId;
    }

    public String getShippingCriteria() {
        return shippingCriteria;
    }

    public void setShippingCriteria(String shippingCriteria) {
        this.shippingCriteria = shippingCriteria;
    }
    
}
