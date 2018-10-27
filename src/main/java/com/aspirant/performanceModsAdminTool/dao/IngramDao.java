/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao;

import com.aspirant.performanceModsAdminTool.model.CurrentWarehouseProduct;
import com.aspirant.performanceModsAdminTool.model.Order;
import java.util.List;

/**
 *
 * @author Ritesh
 */
public interface IngramDao {

    /**
     * this method is use to get Warehouse Identification Number
     *
     * @return list of warehouse identification number
     */
    public List<CurrentWarehouseProduct> getWarehouseIdentificationNo();

    /**
     * Method will update Current WareHouse Product with following parameters
     * For Ingram
     *
     * @param sku
     * @param quantity
     * @param price
     * @return
     */
    public int updateCurrentWareHouseProductForIngram(String sku, int quantity, double price);

    /**
     * Method will update order status against PO_number parameter
     *
     * @param warehouseOrderStatus
     * @param poNumber
     * @return
     */
    public int updateWarehouseOrderStatus(String warehouseOrderStatus, String poNumber, String warehouseOrderNumber);

    /**
     * Method will update update Tracking Carrier And tracking Id, against
     * poNumber parameter
     *
     * @param trackingCarrier
     * @param trackingId
     * @param poNumber
     * @return
     */
    public int updateTrackingCarrierAndId(String trackingCarrier, String trackingId, String poNumber);

    /**
     * method will update Warehouse(Branch) Order Number against poNumber
     * parameter
     *
     * @param warehouseOrderNumber
     * @param poNumber
     * @return
     */
    public int updateIngramOrderResponceData(String warehouseOrderNumber, String poNumber);

    /**
     * flush data before processing request for Ingram PNA
     */
    public void flushDataForIngramPNA();

    /**
     * flush data before processing request for Ingram Order Tracking
     */
    public void flushDataForIngramOrderTracking();

    /**
     * this method will fetch data for Order Request XML
     *
     * @param poNumber
     * @return
     */
    public Order getDataforOrderRequestXML(String poNumber);

    /**
     * this method will fetch data for Order Detail Request XML
     *
     * @return
     */
    public Order getDataForOrderDetailRequest();

    /**
     * this method will fetch data for Invoice Xml
     *
     * @return
     */
    public Order getDataForInvoiceXml();

    /**
     * this method is use to get Po Number
     *
     * @return
     */
    //public String getPoNumberForTracking();
    
    
    public String getPoNumberForOrderStatus();

    /**
     * this method will fetch data for Order Tracking Request XML
     *
     * @return
     */
    public Order getDataForOrderTrackingRequest();
    
    public Order getDataForIngramBaseRateRequest();
}
