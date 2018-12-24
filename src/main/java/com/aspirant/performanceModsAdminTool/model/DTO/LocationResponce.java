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
public class LocationResponce {

    private int id;
    private String type;
    private LocationAttributes attributes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocationAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(LocationAttributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "LocationResponce{" + "id=" + id + ", type=" + type + ", attributes=" + attributes + '}';
    }
    

}
