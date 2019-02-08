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
public class OrderItemDTO {

    private String item_identifier;
    private String item_identifier_type;
    private int quantity;

    public String getItem_identifier() {
        return item_identifier;
    }

    public void setItem_identifier(String item_identifier) {
        this.item_identifier = item_identifier;
    }

    public String getItem_identifier_type() {
        return item_identifier_type;
    }

    public void setItem_identifier_type(String item_identifier_type) {
        this.item_identifier_type = item_identifier_type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" + "item_identifier=" + item_identifier + ", item_identifier_type=" + item_identifier_type + ", quantity=" + quantity + '}';
    }
    
}
