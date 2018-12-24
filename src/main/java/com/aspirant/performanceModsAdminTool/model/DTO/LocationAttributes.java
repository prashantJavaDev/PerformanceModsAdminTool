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
public class LocationAttributes {

    private String Name;
    private String Street;
    private String City;
    private String State;
    private String Country;
    private String ZipCode;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String Street) {
        this.Street = Street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String ZipCode) {
        this.ZipCode = ZipCode;
    }

    @Override
    public String toString() {
        return "LocationAttributes{" + "Name=" + Name + ", Street=" + Street + ", City=" + City + ", State=" + State + ", Country=" + Country + ", ZipCode=" + ZipCode + '}';
    }
    
    
}
