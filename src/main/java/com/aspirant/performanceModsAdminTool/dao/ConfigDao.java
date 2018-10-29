/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.dao;

import com.amazonservices.mws.orders.amazon.AmazonWebService;
import com.aspirant.performanceModsAdminTool.model.AmazonProperties;

/**
 *
 * @author pk
 */
public interface ConfigDao {
    public AmazonProperties getAmazonProperties();
    
}
