/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.service.ManufacturerService;
import com.aspirant.performanceModsAdminTool.dao.ManufacturerDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.Manufacturer;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ritesh
 */
@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    @Autowired
    private ManufacturerDao manufacturerDao;

    @Override
    public int addManufacturer(Manufacturer manufacturer) {
        try {
            int manufacturerId = this.manufacturerDao.addManufacturer(manufacturer);
           // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Manufacturer added successfully for id : " + manufacturerId, GlobalConstants.TAG_SYSTEMLOG));
            return manufacturerId;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public int mapManufacturer(Manufacturer manufacturer) {
        try {
            this.manufacturerDao.mapManufacturer(manufacturer);
           // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Manufacturer mapped successfully.", GlobalConstants.TAG_SYSTEMLOG));
            return 1;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public List<String> GetListofMatchingManufacturerCode(String manufacturerCode) {
        return this.manufacturerDao.GetListofMatchingManufacturerCode(manufacturerCode);
    }

    @Override
    public List<Manufacturer> mapManufacturerList(int manufacturerId) {
        return this.manufacturerDao.mapManufacturerList(manufacturerId);
    }
}
