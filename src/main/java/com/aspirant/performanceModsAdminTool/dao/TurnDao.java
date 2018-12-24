/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao;

import com.aspirant.performanceModsAdminTool.model.DTO.ItemResponse;
import com.aspirant.performanceModsAdminTool.model.DTO.TokenResponse;
import java.util.List;

/**
 *
 * @author pk
 */
public interface TurnDao {

    public int updateTokenEntry(TokenResponse tokenResponse);

    public TokenResponse getToken();
    
     public int addItem(List<ItemResponse> data);

}
