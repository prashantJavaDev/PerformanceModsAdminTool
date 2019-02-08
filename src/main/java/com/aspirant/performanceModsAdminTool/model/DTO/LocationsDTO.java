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
public class LocationsDTO {

    private String location;
    private boolean combine_in_out_stock;
    private List<OrderItemDTO> items;
    private ShippingDTO shipping;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isCombine_in_out_stock() {
        return combine_in_out_stock;
    }

    public void setCombine_in_out_stock(boolean combine_in_out_stock) {
        this.combine_in_out_stock = combine_in_out_stock;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    public ShippingDTO getShipping() {
        return shipping;
    }

    public void setShipping(ShippingDTO shipping) {
        this.shipping = shipping;
    }

    @Override
    public String toString() {
        return "LocationsDTO{" + "location=" + location + ", combine_in_out_stock=" + combine_in_out_stock + ", items=" + items + ", shipping=" + shipping + '}';
    }
}
