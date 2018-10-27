/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.InvoiceDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.Invoice;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import com.aspirant.performanceModsAdminTool.service.InvoiceService;
import static com.aspirant.performanceModsAdminTool.service.impl.OrderServiceImpl.BULK_ORDER_TRACKING_FILE_PATH;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.utils.DateUtils;
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
 * @author Pallavi
 */
@Service
public class InvoiceServiceImpl implements InvoiceService{
    
    @Autowired
    private InvoiceDao invoiceDao;
    
    public static final String INVOICE_FEED_FILE_PATH = "/home/altius/performanceMods/invoiceFeed/";

    @Override
    public int saveMultipartFileData(UploadFeed uploadFeed,int warehouseId,String extension) {
        if (!uploadFeed.getMultipartFile().isEmpty()) {
            String originaFileName = uploadFeed.getMultipartFile().getOriginalFilename();
            Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
            byte[] imgBytes = null;
            try {
                imgBytes = uploadFeed.getMultipartFile().getBytes();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String path = INVOICE_FEED_FILE_PATH + sdf.format(curDate) + "/" + originaFileName;
            File folderFile = new File(INVOICE_FEED_FILE_PATH + sdf.format(curDate));
            try {
                folderFile.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
            File someFile = new File(path);
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(someFile);
                fos.write(imgBytes);
                fos.flush();
                fos.close();
                LogUtils.systemLogger.info(LogUtils.buildStringForLog("File transferred.. :" + originaFileName, GlobalConstants.TAG_SYSTEMLOG));
                
                if(extension.equals("csv")){
                    this.invoiceDao.loadInvoiceFeedDataLocally(path, warehouseId);
                    return 1;
                
                }else if(extension.equals("xls")){
                    this.invoiceDao.loadInvoiceFeedDataLocallyExcelXLS(path, warehouseId);
                    return 1;
                
                }else{
                    this.invoiceDao.loadInvoiceFeedDataLocallyExcelXLSX(path, warehouseId);
                    return 1;
                
                }
                
                
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
    public List<Invoice> invoiceDifference() {
        return this.invoiceDao.invoiceDifference();
    }

     
    
}
