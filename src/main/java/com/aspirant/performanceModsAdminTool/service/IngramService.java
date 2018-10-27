 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

import com.aspirant.performanceModsAdminTool.model.CurrentWarehouseProduct;
import com.aspirant.performanceModsAdminTool.model.Order;
import java.util.List;

/**
 *
 * @author Ritesh
 */
public interface IngramService {

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
    public int updateIngramOrderResponceData(String warehouseOrderStatus, String poNumber);

    /**
     * this method hit ingram URL and get the response fro request parameter
     *
     * @param request contains String XML
     * @param i specify the type of request
     * @return response XML
     */
    public String getIngramResponceXml(String request, int i);

    /**
     * flush data before processing request for Ingram PNA
     */
    public void flushDataForIngramPNA();

    /**
     * this method will fetch data for Order Request XML
     *
     * @return
     */
    public void flushDataForIngramOrderTracking();

    /**
     * this method will fetch data for Order Detail Request XML
     *
     * @return
     */
    public Order getDataForOrderDetailRequest();

    //public String getPNARequestXml();
    /**
     * this method will fetch data for Order Request XML
     *
     * @param poNumber
     * @return
     */
    public String getOrderRequestXml(String poNumber);

    /**
     * generate OrderDetailRequest Xml
     *
     * @return
     */
    public String getOrderDetailRequestXml();

    /**
     * generate OrderStatusRequest Xml
     *
     * @return
     */
    public String getOrderStatusRequestXml();

    /**
     * generate OrderTrackingRequest Xml
     *
     * @return
     */
    public String getOrderTrackingRequestXml();
    
    
    public String getOrderShippingCostXml();

    /**
     * generate Invoice Xml
     *
     * @return
     */
    public String getInvoiceXml();

    /**
     * this method is use to get Po Number
     *
     * @return
     */
    public String getPoNumberForOrderStatus();

    /**
     * get PNA response data
     *
     * @return
     */
    public String getPNAResponcedata();
    
    /**
     * Get Order Shipping cost for order
     * 
     * @return 
     */
}