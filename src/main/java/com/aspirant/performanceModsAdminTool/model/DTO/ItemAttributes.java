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
public class ItemAttributes {

    private String product_name;
    private String part_number;
    private String mfr_part_number;
    private String brand_id;
    private String brand;

    
    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPart_number() {
        return part_number;
    }

    public void setPart_number(String part_number) {
        this.part_number = part_number;
    }

    public String getMfr_part_number() {
        return mfr_part_number;
    }

    public void setMfr_part_number(String mfr_part_number) {
        this.mfr_part_number = mfr_part_number;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "ItemAttributes{" + "product_name=" + product_name + ", part_number=" + part_number + ", mfr_part_number=" + mfr_part_number + ", brand_id=" + brand_id + ", brand=" + brand + ", dimensions=" + '}';
    }

    
    
}
