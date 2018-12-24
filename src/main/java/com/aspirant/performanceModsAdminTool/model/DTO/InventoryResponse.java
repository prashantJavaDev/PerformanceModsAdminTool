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
public class InventoryResponse {

    private String id;
    private String type;
    private InventoryAttributes attributes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public InventoryAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(InventoryAttributes attributes) {
        this.attributes = attributes;
    }
   

    @Override
    public String toString() {
        return "InventoryResponse{" + "id=" + id + ", type=" + type + ", attributes=" + attributes + '}';
    }
    
}
