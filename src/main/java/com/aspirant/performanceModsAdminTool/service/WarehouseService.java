/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

import com.aspirant.performanceModsAdminTool.model.CurrentWarehouseProduct;
import com.aspirant.performanceModsAdminTool.model.DTO.BadDataDTO;
import com.aspirant.performanceModsAdminTool.model.FeedUpload;
import com.aspirant.performanceModsAdminTool.model.ShippingCriteria;
import com.aspirant.performanceModsAdminTool.model.ShippingDetails;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author shrutika
 */
public interface WarehouseService {

    /**
     * method is used to add or update product data from uploaded warehouse feed
     * file.
     *
     * @param warehouseId warehouse id of feed uploaded
     * @return Number of rows inserted in current warehouse product table
     */
    public int saveAndUpdateFeed(int warehouseId);

    /**
     * This method is used to save multipart file in local path and load file
     * data in database for selected warehouse. File contains feeds for given
     * warehouse.
     *
     * @param uploadFeed uploadFeed object which contains multipart file
     * @param warehouseId warehouse id for which we upload feed
     * @return bada data summary list
     */
    public List<BadDataDTO> saveMultipartFileData(UploadFeed uploadFeed, int warehouseId);
    
    //public int saveMultipartFileDataForXLsX(UploadFeed uploadFeed, int warehouseId);

    /**
     * Method is used for export to excel bad data list from uploaded feed
     *
     * @return List of bad data in the uploaded feed
     */
    public List<FeedUpload> badFeedforExport();

    /**
     * method is used to add new warehouse
     *
     * @param warehouse warehouse object containing warehouse information
     * @return warehouse id if success and 0 if error
     */
    public int addWarehouse(Warehouse warehouse);

    /**
     * method is used to get list of warehouses
     *
     * @param active 1-active, 0- inactive
     * @return list of warehouse
     */
    public List<Warehouse> getWarehouseList(boolean active);
    
    /**
     * method is used to get list of warehouses for Shipping Details
     * 
     * @param active 1-active, 0- inactive
     * @return list of warehouse
     */   
    public List<Warehouse> getShippingWarehouseList (boolean active);
    
    /**
     * Method is used to get warehouse list 
     *
     * @param warehouseId warehouse id
     * @return warehouse object
     */
    public Warehouse getWarehouseByWarehouseId(int warehouseId);

    /**
     * method is used to update warehouse information
     *
     * @param warehouse warehouse object containing updated warehouse
     * information
     * @return number of rows updated if success and 0 if error
     */
    public int updateWarehouse(Warehouse warehouse);
    
    /**
     * return list of WarehouseIdentificationNumber
     * @return 
     */
     
     
     
    
}
