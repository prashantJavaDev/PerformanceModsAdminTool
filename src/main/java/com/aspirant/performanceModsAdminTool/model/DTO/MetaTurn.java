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
public class MetaTurn {
 private int total_pages;

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    @Override
    public String toString() {
        return "MetaTurn{" + "total_pages=" + total_pages + '}';
    }
 
}
