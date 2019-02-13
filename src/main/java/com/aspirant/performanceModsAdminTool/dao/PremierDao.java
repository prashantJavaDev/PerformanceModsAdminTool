/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao;

import java.util.List;

/**
 *
 * @author pk
 */
public interface PremierDao {

    public List<String> getFeedEntries();

    public int updateTokenOfPremier(String sessionToken, int warehouseId);

    public int addInventoryFile(String path, int warehouseId);

    public int addPriceFile(String path, int warehouseId);
}
