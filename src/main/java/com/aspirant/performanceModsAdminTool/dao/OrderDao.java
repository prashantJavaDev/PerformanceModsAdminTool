/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao;

import com.aspirant.performanceModsAdminTool.model.Listing;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.ProcessingSheet;
import com.aspirant.performanceModsAdminTool.model.ProductDetails;

import java.util.List;

/**
 *
 * @author Ritesh
 */
public interface OrderDao {

    /**
     * Method is used to get Order list.
     *
     * @return list of Order
     */
    public List<Order> getMarketplaceOrderList(int marketplaceId, String marketplaceOrderId, String poNumber, int pageNo, String customerName, String marketplaceSku, String marketplaceListingId, String orderStatus, String fulfillmentChannel, String startDate, String stopDate);

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
     * @return List of Listing object
     */
    public List<Listing> getMarketplaceSkuList();

    /**
     * Method will load warehouse feed file data in database.
     *
     * @param path path in which we save uploaded bulk order tracking feed file
     * @param marketplaceId marketplaceId id for which we upload feed
     *
     */
    public void loadBulkOrderTrackingDataLocally(String path, int marketplaceId);

    /**
     * This method is used to get list of Marketplace Sku
     *
     * @return List of Listing object
     */
    public List<String> getMarketplaceSkuListForFees();

    /**
     *
     * @return list of unshiped order
     */
    public List<Order> getOrderListOfUnshippedOrderForDashboard(int count);

    public List<Order> getOrderListForTrackingUpdate();

    public List<Order> getOderListForCreatingXml();

    // public Order getDataForOrderRequestXML();
    public void updateTrackingRecord(String trackingID);

    public void updateOrderAcknowledgementRecord(String orderId);

    public String getPoNumberBymarketplaceOrderID(String marketplaceOrderId);

    public String getMaxPoNumberForAutogeneration();

    public String getOrderStatusOnMarketplaceOrderId(String marketplaceOrderId);

    public int getCountOfTicketAssignedToOrder(String marketplaceOrderId);
    
    public Order getOrderForAmazonAcknowledgement();
    
    public void loadMarketPlaceOrderDataLocally(String path, int marketplaceId);
}
