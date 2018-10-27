/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao;

import com.aspirant.performanceModsAdminTool.model.Marketplace;
import java.util.List;

/**
 *
 * @author Ritesh
 */
public interface MarketplaceDao {

    /**
     * Method used to add new marketplace.
     *
     * @param marketplace marketplace object
     * @return marketplace id
     */
    public int addMarketplace(Marketplace marketplace);

    /**
     * Method is used to get marketplace list.
     *
     * @param active 1-active marketplace list 0-all marketplace list
     * @return list of marketplaces
     *
     */
    public List<Marketplace> getMarketplaceList(boolean active);

    /**
     * Method is used to get marketplace object by marketplace id
     *
     * @param marketplaceId marketplace id
     * @return marketplace object
     */
    public Marketplace getMarketplaceByMarketplaceId(int marketplaceId);

    /**
     * Method is used to update information of marketplace
     *
     * @param marketplace marketplace object
     * @return number of rows updated
     */
    public int updateMarketplace(Marketplace marketplace);
    
    /**
     * process order automatically
     */
    public void processOrderAutomatically();
}
