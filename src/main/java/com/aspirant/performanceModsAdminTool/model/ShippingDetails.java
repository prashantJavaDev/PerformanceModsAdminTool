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
public class ShippingDetails implements Serializable {

    private int shippingDetailsId;
    private ShippingCriteria shippingCriteria;
    private Warehouse warehouse;
    private Double minValuePrice;
    private Double minValuePriceShipping;
    private Double maxValuePrice;
    private Double maxValuePriceShipping;
    private Double minValueWeight;
    private Double minValueWeightShipping;
    private Double maxValueWeight;
    private Double maxValueWeightShipping;
    private Double shippingCost;
    private Double flatRateValue;

    public Double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(Double shippingCost) {
        this.shippingCost = shippingCost;
    }
    
    public int getShippingDetailsId() {
        return shippingDetailsId;
    }

    public void setShippingDetailsId(int shippingDetailsId) {
        this.shippingDetailsId = shippingDetailsId;
    }

    public ShippingCriteria getShippingCriteria() {
        return shippingCriteria;
    }

    public void setShippingCriteria(ShippingCriteria shippingCriteria) {
        this.shippingCriteria = shippingCriteria;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public Double getMinValuePrice() {
        return minValuePrice;
    }

    public void setMinValuePrice(Double minValuePrice) {
        this.minValuePrice = minValuePrice;
    }

    public Double getMaxValuePrice() {
        return maxValuePrice;
    }

    public void setMaxValuePrice(Double maxValuePrice) {
        this.maxValuePrice = maxValuePrice;
    }

    public Double getMinValueWeight() {
        return minValueWeight;
    }

    public void setMinValueWeight(Double minValueWeight) {
        this.minValueWeight = minValueWeight;
    }

    public Double getMaxValueWeight() {
        return maxValueWeight;
    }

    public void setMaxValueWeight(Double maxValueWeight) {
        this.maxValueWeight = maxValueWeight;
    }

    public Double getFlatRateValue() {
        return flatRateValue;
    }

    public void setFlatRateValue(Double flatRateValue) {
        this.flatRateValue = flatRateValue;
    }

    public Double getMinValuePriceShipping() {
        return minValuePriceShipping;
    }

    public void setMinValuePriceShipping(Double minValuePriceShipping) {
        this.minValuePriceShipping = minValuePriceShipping;
    }

    public Double getMinValueWeightShipping() {
        return minValueWeightShipping;
    }

    public void setMinValueWeightShipping(Double minValueWeightShipping) {
        this.minValueWeightShipping = minValueWeightShipping;
    }

    public Double getMaxValueWeightShipping() {
        return maxValueWeightShipping;
    }

    public void setMaxValueWeightShipping(Double maxValueWeightShipping) {
        this.maxValueWeightShipping = maxValueWeightShipping;
    }

    public Double getMaxValuePriceShipping() {
        return maxValuePriceShipping;
    }

    public void setMaxValuePriceShipping(Double maxValuePriceShipping) {
        this.maxValuePriceShipping = maxValuePriceShipping;
    }

    @Override
    public String toString() {
        return "ShippingDetails{" + "shippingDetailsId=" + shippingDetailsId + ", shippingCriteria=" + shippingCriteria + ", warehouse=" + warehouse + ", minValuePrice=" + minValuePrice + ", minValuePriceShipping=" + minValuePriceShipping + ", maxValuePrice=" + maxValuePrice + ", maxValuePriceShipping=" + maxValuePriceShipping + ", minValueWeight=" + minValueWeight + ", minValueWeightShipping=" + minValueWeightShipping + ", maxValueWeight=" + maxValueWeight + ", maxValueWeightShipping=" + maxValueWeightShipping + ", flatRateValue=" + flatRateValue + '}';
    }

    
}
