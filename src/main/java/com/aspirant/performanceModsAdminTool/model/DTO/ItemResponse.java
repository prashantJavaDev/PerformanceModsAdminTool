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
public class ItemResponse {

    private String id;
    private String type;
    private ItemAttributes attributes;

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

    public ItemAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(ItemAttributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "ItemResponse{" + "id=" + id + ", type=" + type + ", attributes=" + attributes + '}';
    }
    
}
