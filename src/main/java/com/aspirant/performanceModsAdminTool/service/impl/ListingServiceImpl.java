/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.service.ListingService;
import com.aspirant.performanceModsAdminTool.dao.ListingDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.DTO.ProductListingDTO;
import com.aspirant.performanceModsAdminTool.model.Listing;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ritesh
 */
@Service
public class ListingServiceImpl implements ListingService {

    @Autowired
    private ListingDao listingDao;
    public static final String FEES_FILE_PATH = "/home/altius/performanceMods/fees/";

    @Override
    public int saveMultipartFileData(UploadFeed uploadFeed, int marketplaceId) {
        if (!uploadFeed.getMultipartFile().isEmpty()) {
            String originaFileName = uploadFeed.getMultipartFile().getOriginalFilename();
            Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
            byte[] imgBytes = null;
            try {
                imgBytes = uploadFeed.getMultipartFile().getBytes();
            } catch (IOException ex) {
                Logger.getLogger(WarehouseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String path = FEES_FILE_PATH + sdf.format(curDate) + "/" + originaFileName;
            File folderFile = new File(FEES_FILE_PATH + sdf.format(curDate));
            try {
                folderFile.mkdirs();
            } catch (Exception e) {
                Logger.getLogger(ListingServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            }
            File someFile = new File(path);
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(someFile);
                fos.write(imgBytes);
                fos.flush();
                fos.close();
                LogUtils.systemLogger.info(LogUtils.buildStringForLog("File transferred.. :" + originaFileName, GlobalConstants.TAG_SYSTEMLOG));
                this.listingDao.loadFeesDataLocally(path, marketplaceId);
                return 1;
            } catch (FileNotFoundException ex) {
                LogUtils.systemLogger.warn(LogUtils.buildStringForLog("FileNotFoundException :" + ex, GlobalConstants.TAG_SYSTEMLOG));
                return 0;
            } catch (Exception e) {
                LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
                return 0;
            }
        }
        return 0;
    }

    @Override
    public List<ProductListingDTO> getListingList(int marketplceId, int warehouseId, String marketplaceSku, int pageNo, boolean active, int[] manufacturerId) {
        return this.listingDao.getListingList(marketplceId, warehouseId, marketplaceSku, pageNo, active, manufacturerId);
    }

    @Override
    public int getListingCount(int marketplaceId, int warehouseId, String marketplaceSku, boolean active, int[] manufacturerId) {
        return this.listingDao.getListingCount(marketplaceId, warehouseId, marketplaceSku, active, manufacturerId);
    }

    @Override
    public int updatePriceQuantity(String sku, double price, int quantity, double profit, boolean active) {
        try {
            return this.listingDao.updatePriceQuantity(sku, price, quantity, profit, active);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public int UpdateListings(int marketplaceId) {
        try {
            this.listingDao.UpdateListings(marketplaceId);
            LogUtils.systemLogger.info(LogUtils.buildStringForLog("listing confirmed", GlobalConstants.TAG_SYSTEMLOG));
            return 1;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public List<Integer> getProfitPercentageList() {
        return this.listingDao.getProfitPercentageList();
    }

    @Override
    public List<String> searchSku(String term) {
        return this.listingDao.searchSku(term);
    }

    @Override
    public int processListing(int marketplaceId, int manufacturerId) {
        try {
            int result = this.listingDao.processListing(marketplaceId, manufacturerId);
            LogUtils.systemLogger.info(LogUtils.buildStringForLog(+result + ": listing updated", GlobalConstants.TAG_SYSTEMLOG));
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public List<Listing> exportMarketplaceFees(int marketplaceId, int pageNo) {
        return this.listingDao.exportMarketplaceFees(marketplaceId, pageNo);
    }

    @Override
    public int getExportMarketplaceFeesCount(int marketplaceId) {
        return this.listingDao.getExportMarketplaceFeesCount(marketplaceId);
    }

    @Override
    public int saveMultipartFileData1(UploadFeed uploadFeed, int marketplaceId) {
        if (!uploadFeed.getMultipartFile().isEmpty()) {
            String originaFileName = uploadFeed.getMultipartFile().getOriginalFilename();
            Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
            byte[] imgBytes = null;
            try {
                imgBytes = uploadFeed.getMultipartFile().getBytes();
            } catch (IOException ex) {
                Logger.getLogger(WarehouseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String path = FEES_FILE_PATH + sdf.format(curDate) + "/" + originaFileName;
            File folderFile = new File(FEES_FILE_PATH + sdf.format(curDate));
            try {
                folderFile.mkdirs();
            } catch (Exception e) {
                Logger.getLogger(ListingServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            }
            File someFile = new File(path);
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(someFile);
                fos.write(imgBytes);
                fos.flush();
                fos.close();
                LogUtils.systemLogger.info(LogUtils.buildStringForLog("File transferred.. :" + originaFileName, GlobalConstants.TAG_SYSTEMLOG));
                this.listingDao.loadFeesDataLocally1(path, marketplaceId);
                return 1;
            } catch (FileNotFoundException ex) {
                LogUtils.systemLogger.warn(LogUtils.buildStringForLog("FileNotFoundException :" + ex, GlobalConstants.TAG_SYSTEMLOG));
                return 0;
            } catch (Exception e) {
                LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
                return 0;
            }
        }
        return 0;
    }

    @Override
    public List<Listing> exportMarketplaceFeesForExcel(int marketplaceId) {
        return this.listingDao.exportMarketplaceFeesForExcel(marketplaceId);
    }
}
