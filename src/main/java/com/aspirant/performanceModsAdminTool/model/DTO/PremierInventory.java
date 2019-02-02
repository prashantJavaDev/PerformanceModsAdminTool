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
public class PremierInventory {

    private String warehouseCode;
    private int quantityAvailable;

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    @Override
    public String toString() {
        return "PremierInventory{" + "warehouseCode=" + warehouseCode + ", quantityAvailable=" + quantityAvailable + '}';
    }

}
