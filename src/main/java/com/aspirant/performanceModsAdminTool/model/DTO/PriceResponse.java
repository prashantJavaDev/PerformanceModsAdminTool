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
public class PriceResponse {

    private String id;
    private String type;
    private PriceAttributes attributes;

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

    public PriceAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(PriceAttributes attributes) {
        this.attributes = attributes;
    }

   

    @Override
    public String toString() {
        return "PriceResponse{" + "id=" + id + ", type=" + type + ", attributes=" + attributes + '}';
    }
    
}
