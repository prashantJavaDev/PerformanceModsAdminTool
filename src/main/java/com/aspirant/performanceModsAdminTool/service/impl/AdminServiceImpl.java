/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.AdminDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.Company;
import com.aspirant.performanceModsAdminTool.model.Listing;
import com.aspirant.performanceModsAdminTool.model.ShippingCriteria;
import com.aspirant.performanceModsAdminTool.model.ShippingDetails;
import com.aspirant.performanceModsAdminTool.service.AdminService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author shrutika
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;

    @Override
    public int insertCategoryMapping(int mainCategoryId, int[] assignCategories) {
        try {
            this.adminDao.insertCategoryMapping(mainCategoryId, assignCategories);
            return 1;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public int addNewListing(Listing listing) {
        try {
            this.adminDao.addNewListing(listing);
            return 1;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }

    }

    @Override
    public String generateSku(String productMpn, int manufacturerId) {
        List<String> skuList = this.adminDao.getSkuList(productMpn, manufacturerId);
        String performanceModsMpn = this.adminDao.getperformanceModsMpnByMPN(productMpn);
        int num = skuList.size() + 1;
        String newSku = performanceModsMpn + "_" + num;
        int i = 0;
        for (String sku : skuList) {
            if (sku == newSku) {
                i++;
            }
        }
        if (i == 0) {
            return newSku;
        } else {
            return null;
        }
    }

    @Override
    public int addCompany(Company company) {
        return this.adminDao.addCompany(company);
    }

    @Override
    public List<Company> getCompanyList(boolean active) {
        return this.adminDao.getCompanyList(active);
    }

    @Override
    public Company getcompanyBycompanyID(int companyId) {
        return this.adminDao.getcompanyBycompanyID(companyId);
    }

    @Override
    public int updateCompany(Company company) {
        try {
            int result = this.adminDao.updateCompany(company);
            LogUtils.systemLogger.info(LogUtils.buildStringForLog("Company Updated Successfully.", GlobalConstants.TAG_SYSTEMLOG));
            return result;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public List<ShippingCriteria> getshippingCriteriaList() {
        return this.adminDao.getshippingCriteriaList();
    }

    @Override
    public int addWarehouseShipping(ShippingDetails shippingDetails, int criteriaId, int warehouseId) {
        return this.adminDao.addWarehouseShipping(shippingDetails, criteriaId, warehouseId);
    }

    @Override
    public ShippingDetails getWarehouseShippingDetailsByDetailsId(int warehouseShippingDetailsId) {
        return this.adminDao.getWarehouseShippingDetailsByDetailsId(warehouseShippingDetailsId);
    }

    @Override
    public int insertChildCategoryMapping(int subCategoryId, int[] assignChildCategories) {
        try {
            this.adminDao.insertChildCategoryMapping(subCategoryId, assignChildCategories);
            return 1;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public int insertSubChildCategoryMapping(int childCategoryId, int[] assignSubChildCategories) {
        try {
            this.adminDao.insertSubChildCategoryMapping(childCategoryId, assignSubChildCategories);
            return 1;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
        
    }
    
   
}
