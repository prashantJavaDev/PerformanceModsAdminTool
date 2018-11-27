/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

import com.aspirant.performanceModsAdminTool.model.Listing;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.ProcessingSheet;
import com.aspirant.performanceModsAdminTool.model.ProductDetails;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import java.util.List;

/**
 *
 * @author Ritesh
 */
public interface OrderService {

    /**
     * This method is used to save multipart file in local path and load file
     * data in database for selected marketplace. File contains bulk order data
     * for given marketplace.
     *
     * @param uploadFeed uploadFeed object which contains multipart file
     * @param marketplaceId marketplace id for which we upload commission
     * @return 1-Success, 0-Error
     */
    public int saveMultipartFileData(UploadFeed uploadFeed, int marketplaceId);


    public List<Order> getOrderList(int marketplaceId, String marketplaceOrderId, String poNumber, int pageNo, String customerName, String marketplaceSku, String marketplaceListingId, String orderStatus, String fulfillmentChannel, String startDate, String stopDate);

    /**
     * This method is used to get order by marketplace Id
     *
     * @param marketplaceOrderId marketplace Id
     * @return object of order
     */
    public Order getorderBymarketplaceOrderID(String marketplaceOrderId);

    /**
     * This method is used to get order trans data by marketplace order Id
     *
     * @param marketplaceOrderId marketplace order Id
     * @return list of order
     */
    public List<Order> getorderTransBymarketplaceOrderID(String marketplaceOrderId);

    /**
     * This method is used to get product list by marketplace Sku
     *
     * @param marketplaceSku marketplace sku
     * @return list of product Details
     */
    public List<ProductDetails> getProductListOnMarketplaceSku(String marketplaceSku);

    /**
     * This method is used to get order count
     *
     * @param marketplaceId marketplace Id
     * @param marketplaceOrderId marketplace Order Id
     * @param customerName customer Name
     * @param marketplaceSku marketplace sku
     * @param marketplaceListingId marketplace listing id
     * @param orderStatus order status
     * @param fulfillmentChannel fulfillment channel
     * @param startDate start date
     * @param stopDate stop date
     * @return count of orders
     */
    public int getOrderCount(int marketplaceId, String marketplaceOrderId, String poNumber, String customerName, String marketplaceSku, String marketplaceListingId, String orderStatus, String fulfillmentChannel, String startDate, String stopDate);

    /**
     * This method is used to assign warehouse to process order
     *
     * @param warehouseId warehouse id
     * @param orderId order id
     */
    public void assignWarehouseToOrder(int warehouseId, String orderId);

    /**
     * This method is used to get processed order count
     *
     * @param warehouseId warehouse Id
     * @param currentDate current date
     * @return count of processed orders
     */
    public int getProcessOrderCount(int warehouseId, String currentDate);

    /**
     * This method is used to get processed order list
     *
     * @param warehouseId warehouse Id
     * @param currentDate current date
     * @return count of processed orders
     *
     */
    public List<ProcessingSheet> getProcessOrderList(int warehouseId, String currentDate);

    /**
     * This method is used to add tracking for order
     *
     * @param order object of order
     * @param warehouseId
     * @return 1 if success 0 if fail
     */
    public int addOrderTracking(Order order, int warehouseId);

    /**
     * This method is used to get list of Marketplace Sku
     *
     * @return List of sku
     */
    public List<Listing> getMarketplaceSkuList();

    /**
     * This method is used to get Order List Of Unshipped Order For Dashboard
     *
     * @return list of unshiped order
     */
    public List<Order> getOrderListOfUnshippedOrderForDashboard(int count);

    public List<Order> getOrderListForTrackingUpdate();

    public List<Order> getOderListForCreatingXml();

    public String createAndSaveXMLFile();

    public void updateTrackingRecord(String trackingID);

    public String getPoNumberBymarketplaceOrderID(String marketplaceOrderId);
//    public void createAndSaveXMLFileForPNARequest();
    //public Order getMaxPoNumberForAutogeneration();

    public String getOrderStatusOnMarketplaceOrderId(String marketplaceOrderId);

    public int getCountOfTicketAssignedToOrder(String marketplaceOrderId);

    public void updateOrderAcknowledgementRecord(String orderId);
     
     public String amazonAcknowledgementFile();
     
     public int saveMultipartFileForMarketPlaceOrderUpload(UploadFeed uploadFeed, int marketplaceId);
}
