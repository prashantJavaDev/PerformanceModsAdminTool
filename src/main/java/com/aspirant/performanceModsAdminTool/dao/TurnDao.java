/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao;

import com.aspirant.performanceModsAdminTool.model.DTO.ItemResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.TokenResponse;
import com.aspirant.performanceModsAdminTool.web.controller.ShippingTurnAPIResponse;
import java.util.List;

/**
 *
 * @author pk
 */
public interface TurnDao {

    public int updateTokenEntry(TokenResponse tokenResponse, int warehouseId);

    public TokenResponse getToken(String apiType);

    public int addItem(List<ItemResponse> data);

    public int addItemByFile(String path, int warehouseId);

    public int addPriceFile(String path, int warehouseId);

    public int addInventoryFile(String path, int warehouseId);

    public void addShipping(ShippingTurnAPIResponse data);

}
