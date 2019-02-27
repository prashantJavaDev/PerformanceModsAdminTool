/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao;

import com.aspirant.performanceModsAdminTool.model.DTO.MayerTokenResponse;
import java.util.List;

/**
 *
 * @author pk
 */
public interface MayersDao {

    public int updateTokenEntry(MayerTokenResponse tokenResponse);

    public List<String> getFeedEntries();

    public int addItemFile(String path);
}
