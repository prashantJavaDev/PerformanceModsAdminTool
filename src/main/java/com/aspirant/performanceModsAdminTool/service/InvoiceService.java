/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.service;

import com.aspirant.performanceModsAdminTool.model.Invoice;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import java.util.List;

/**
 *
 * @author Pallavi
 */

public interface InvoiceService {
     /**
      * This method is used to save multipart file in local path and load file
     * data in database for selected warehouse. Invoice file contains Purchas 
      * @param uploadFeed
      * @param warehouseId
      * @param extension
      * @return success or failure int value
      */
     public int saveMultipartFileData(UploadFeed uploadFeed,int warehouseId,String extension);
    /**
     * method return the difference between feeds and invoice uploaded file
     * @return the list of invoice difference
     */
     public List<Invoice> invoiceDifference();
}
