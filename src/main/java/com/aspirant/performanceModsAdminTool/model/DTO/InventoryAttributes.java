/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.DTO;

import java.util.List;
import java.util.Map;

/**
 *
 * @author pk
 */
public class InventoryAttributes {

    private Object inventory;
    private ManufacturerAPIDTO manufacturer;

    public Object getInventory() {
        return inventory;
    }

    public void setInventory(Object inventory) {
        this.inventory = inventory;
    }

    public ManufacturerAPIDTO getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerAPIDTO manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "InventoryAttributes{" + "inventory=" + inventory + ", manufacturer=" + manufacturer + '}';
//        return "InventoryAttributes{ manufacturer=" + manufacturer + '}';
//        return "InventoryAttributes{inventory=" + inventory + '}';
    }

}
