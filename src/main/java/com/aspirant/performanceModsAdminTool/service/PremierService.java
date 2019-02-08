/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

/**
 *
 * @author pk
 */
public interface PremierService {
    
    
    public String getSessionToken(int warehouseId);
    
    public void getInventory(int warehouseId); 
    
    public void getPrice(int warehouseId); 
}
