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
public class RecipientDTO {

    private String name;
    private String address;
    private String address_2;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String email_address;
    private String phone_number;
    private boolean is_shop_address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public boolean isIs_shop_address() {
        return is_shop_address;
    }

    public void setIs_shop_address(boolean is_shop_address) {
        this.is_shop_address = is_shop_address;
    }

    @Override
    public String toString() {
        return "RecipientDTO{" + "name=" + name + ", address=" + address + ", address_2=" + address_2 + ", city=" + city + ", state=" + state + ", country=" + country + ", zip=" + zip + ", email_address=" + email_address + ", phone_number=" + phone_number + ", is_shop_address=" + is_shop_address + '}';
    }
    
}
