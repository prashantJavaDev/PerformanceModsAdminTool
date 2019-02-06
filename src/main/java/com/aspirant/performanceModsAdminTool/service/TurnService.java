/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

import com.aspirant.performanceModsAdminTool.model.DTO.ItemResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.TokenResponse;
import java.util.List;

/**
 *
 * @author pk
 */
public interface TurnService {

    public int updateTokenEntry(TokenResponse tokenResponse,int warehouseId);

    public TokenResponse getToken(String apiType);

    public void getApiTokenOfTurn(int warehouseId);

    public void getLocation();

    public void getItems(int warehouseId);

    public void getPrice(int warehouseId);

    public void getInventory(int warehouseId);

    public int addItem(List<ItemResponse> data);

    public int addItemByFile();

    public int addPriceByFile();

    public int addInventoryByFile();
}
