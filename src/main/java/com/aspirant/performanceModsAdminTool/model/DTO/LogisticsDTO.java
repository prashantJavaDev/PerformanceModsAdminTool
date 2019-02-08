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
public class LogisticsDTO {

    private int dropship_controller_id;
    private String days;
    private String location;

    public int getDropship_controller_id() {
        return dropship_controller_id;
    }

    public void setDropship_controller_id(int dropship_controller_id) {
        this.dropship_controller_id = dropship_controller_id;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "LogisticsDTO{" + "dropship_controller_id=" + dropship_controller_id + ", days=" + days + ", location=" + location + '}';
    }
    
}
