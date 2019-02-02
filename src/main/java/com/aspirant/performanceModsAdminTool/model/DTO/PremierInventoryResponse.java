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
public class PremierInventoryResponse {

    private String itemNumber;
    private List<PremierInventory> inventory;

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public List<PremierInventory> getInventory() {
        return inventory;
    }

    public void setInventory(List<PremierInventory> inventory) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return "PremierInventoryResponse{" + "itemNumber=" + itemNumber + ", inventory=" + inventory + '}';
    }

}
