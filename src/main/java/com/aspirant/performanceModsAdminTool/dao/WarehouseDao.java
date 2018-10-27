/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao;

import com.aspirant.performanceModsAdminTool.model.CurrentWarehouseProduct;
import com.aspirant.performanceModsAdminTool.model.DTO.BadDataDTO;
import com.aspirant.performanceModsAdminTool.model.FeedUpload;
import com.aspirant.performanceModsAdminTool.model.ShippingCriteria;
import com.aspirant.performanceModsAdminTool.model.ShippingDetails;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author shrutika
 */
public interface WarehouseDao {

    /**
     * method is used to get list of warehouses
     *
     * @param active 1-active, 0- inactive
     * @return list of warehouses
     */
    public List<Warehouse> getWarehouseList(boolean active);

    /**
     * method is used to get list of warehouses for Shipping Details
     *
     * @param active 1-active, 0- inactive
     * @return list of warehouse
     */
    public List<Warehouse> getShippingWarehouseList(boolean active);

    /**
     * method is used to add or update product data from uploaded warehouse feed
     * file.
     *
     * @param warehouseId warehouse id of feed uploaded
     * @return Number of rows inserted in current warehouse product table
     */
    public int saveAndUpdateFeed(int warehouseId);

    /**
     * Method will load warehouse feed .csv file  data in database. It also validates
     * feed data and updates reason.
     *
     * @param path path in which we save uploaded warehouse feed file
     * @param warehouseId warehouse id for which we upload feed
     * @return List of bad data in the uploaded feed
     * @throws java.io.FileNotFoundException
     */
    public List<BadDataDTO> loadDataLocally(String path, int warehouseId);
    
    //public void loadDatalocallyForXLSx(String path, int warehouseId) throws FileNotFoundException, IOException ;
     
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
     * @return warehouse id
     */
    public int addWarehouse(Warehouse warehouse);

    /**
     * Method is used to get warehouse object by warehouse Id
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
     * @return number of rows updated
     */
    public int updateWarehouse(Warehouse warehouse);
    
    
    
}
