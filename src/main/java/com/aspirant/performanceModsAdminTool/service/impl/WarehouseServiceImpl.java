/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.WarehouseDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.CurrentWarehouseProduct;
import com.aspirant.performanceModsAdminTool.model.DTO.BadDataDTO;
import com.aspirant.performanceModsAdminTool.model.FeedUpload;
import com.aspirant.performanceModsAdminTool.model.ShippingCriteria;
import com.aspirant.performanceModsAdminTool.model.ShippingDetails;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import com.aspirant.performanceModsAdminTool.service.WarehouseService;
//import static com.aspirant.performanceModsAdminTool.test.ReadXml2.XMLFilePath;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author shrutika
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseDao warehouseDao;
    public static final String IMG_FILE_PATH = "/home/ubuntu/performanceMods/feeds/";

    /*
     * column 0 - Manufacturer
     * column 1 - MPN
     * column 2 - MAP
     * column 3 - MSRP
     * column 4 - Price
     * column 5 - Quantity
     * column 6 - Condition
     * column 7 - WarehouseIdenticationNo 
     * column 8 - Weight
     * column 9 - EstShippingWt
     * column 10 - Length
     * column 11 - Width
     * column 12 - Height
     * column 13 - UPC
     * column 14 - Short Desc
     * column 15 - Long Desc
     * column 16 - Resize Image
     * column 17 - Large Image
     * column 18 - Shipping
     */
    @Override
    public int saveAndUpdateFeed(int warehouseId) {
        try {
            return this.warehouseDao.saveAndUpdateFeed(warehouseId);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return -1;
        }
    }

    @Override
    public List<BadDataDTO> saveMultipartFileData(UploadFeed uploadFeed, int warehouseId) {
        List<BadDataDTO> feedSummaryList = new LinkedList<BadDataDTO>();
        String file = uploadFeed.getMultipartFile().getOriginalFilename();
        file = file.replace(".", ",");
        String[] fileExtension = file.split(",");

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
            String path = IMG_FILE_PATH + sdf.format(curDate) + "/" + originaFileName;
            File folderFile = new File(IMG_FILE_PATH + sdf.format(curDate));
            try {
                folderFile.mkdirs();
            } catch (Exception e) {
                Logger.getLogger(WarehouseServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            }
            File someFile = new File(path);
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(someFile);
                fos.write(imgBytes);
                fos.flush();
                fos.close();
                // LogUtils.systemLogger.info(LogUtils.buildStringForLog("File transferred.. :" + originaFileName + "-Warehouse Id :" + warehouseId, GlobalConstants.TAG_SYSTEMLOG));
                feedSummaryList = this.warehouseDao.loadDataLocally(path, warehouseId);

            } catch (FileNotFoundException ex) {
                LogUtils.systemLogger.warn(LogUtils.buildStringForLog("FileNotFoundException :" + ex, GlobalConstants.TAG_SYSTEMLOG));
                feedSummaryList = null;
            } catch (Exception e) {
                LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
                feedSummaryList = null;
            }
        }
        return feedSummaryList;
    }

    @Override
    public List<FeedUpload> badFeedforExport() {
        return this.warehouseDao.badFeedforExport();
    }

    @Override
    public int addWarehouse(Warehouse warehouse) {
        try {
            int warehouseId = this.warehouseDao.addWarehouse(warehouse);
            // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Warehouse added successfully for id : " + warehouseId, GlobalConstants.TAG_SYSTEMLOG));
            return warehouseId;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public List<Warehouse> getWarehouseList(boolean active) {
        return this.warehouseDao.getWarehouseList(active);
    }

    @Override
    public Warehouse getWarehouseByWarehouseId(int warehouseId) {
        return this.warehouseDao.getWarehouseByWarehouseId(warehouseId);
    }

    @Override
    public int updateWarehouse(Warehouse warehouse) {
        try {
            int result = this.warehouseDao.updateWarehouse(warehouse);
            // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Warehouse Updated Successfully.", GlobalConstants.TAG_SYSTEMLOG));
            return result;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public List<Warehouse> getShippingWarehouseList(boolean active) {
        return this.warehouseDao.getShippingWarehouseList(active);
    }

}
