/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.MarketplaceDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.Marketplace;
import com.aspirant.performanceModsAdminTool.service.MarketplaceService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ritesh
 */
@Service
public class MarketplaceServiceImpl implements MarketplaceService {

    @Autowired
    private MarketplaceDao marketplaceDao;

    @Override
    public int addMarketplace(Marketplace marketplace) {
        try {
            int marketplaceId = this.marketplaceDao.addMarketplace(marketplace);
           // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Marketplace added successfully for id : " + marketplaceId, GlobalConstants.TAG_SYSTEMLOG));
            return marketplaceId;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public List<Marketplace> getMarketplaceList(boolean active) {
        return this.marketplaceDao.getMarketplaceList(active);
    }

    @Override
    public Marketplace getMarketplaceByMarketplaceId(int marketplaceId) {
        return this.marketplaceDao.getMarketplaceByMarketplaceId(marketplaceId);
    }

    @Override
    public int updateMarketplace(Marketplace marketplace) {
        try {
            int result = this.marketplaceDao.updateMarketplace(marketplace);
           // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Marketplace Updated Successfully.", GlobalConstants.TAG_SYSTEMLOG));
            return result;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }
    
    public void processOrderAutomatically(){
      this.marketplaceDao.processOrderAutomatically();
    }

}
