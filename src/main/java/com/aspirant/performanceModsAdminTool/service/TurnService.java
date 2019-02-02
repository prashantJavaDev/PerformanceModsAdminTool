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

    public int updateTokenEntry(TokenResponse tokenResponse);

    public TokenResponse getToken(String apiType);

    public void getApiTokenOfTurn();

    public void getLocation();

    public void getItems();

    public void getPrice();

    public void getInventory();

    public int addItem(List<ItemResponse> data);

    public int addItemByFile();

    public int addPriceByFile();

    public int addInventoryByFile();
}
