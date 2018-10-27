/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.text.spi.DecimalFormatSymbolsProvider;
import java.util.Date;

/**
 *
 * @author Ritesh
 */
public class Order implements Serializable {

    private int orderId;
    private String marketplaceOrderId;
    private String marketplaceSku;
    private String marketplaceListingId;
    private Date orderDate;
    private Date shipByDate;
    private Date deliveryByDate;
    private Date paymentDate;
    private Date lastUpdatedDate;
    private String customerName;
    private String customerPhoneNo;
    private int quantityUnshipped;
    private int quantityShipped;
    private double price;
    private double tax;
    private double shipping;
    private String shipToName;
    private String shippingAddressLine1;
    private String shippingAddressLine2;
    private String shippingAddressLine3;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String shippingPhoneNo;
    private String orderStatus;
    private String fulfillmentChannel;
    private int customerId;
    private User processedBy;
    private Date processedDate;
    private Marketplace marketplace;
    private Manufacturer manufacturer;
    private Warehouse warehouse;
    private String trackingId;
    private String trackingCarrier;
    private User trackingBy;
    private Date trackingDate;
    private Date shippingDate;
    private MpnSkuMapping mpnSkuMapping;
    private String orderItemId;
    private String warehouesIdeNum;
    private double priceCrntWHprct;
    private String poNumber;
    private String warehouseOrderNumber;
    private String updatedBy;
     
    
    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public String getWarehouesIdeNum() {
        return warehouesIdeNum;
    }

    public void setWarehouesIdeNum(String warehouesIdeNum) {
        this.warehouesIdeNum = warehouesIdeNum;
    }

    public double getPriceCrntWHprct() {
        return priceCrntWHprct;
    }

    public void setPriceCrntWHprct(double priceCrntWHprct) {
        this.priceCrntWHprct = priceCrntWHprct;
    }
    
    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public User getTrackingBy() {
        return trackingBy;
    }

    public void setTrackingBy(User trackingBy) {
        this.trackingBy = trackingBy;
    }

    public Date getTrackingDate() {
        return trackingDate;
    }

    public void setTrackingDate(Date trackingDate) {
        this.trackingDate = trackingDate;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getTrackingCarrier() {
        return trackingCarrier;
    }

    public void setTrackingCarrier(String trackingCarrier) {
        this.trackingCarrier = trackingCarrier;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public User getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(User processedBy) {
        this.processedBy = processedBy;
    }

   

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

    public Marketplace getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public String getFulfillmentChannel() {
        return fulfillmentChannel;
    }

    public void setFulfillmentChannel(String fulfillmentChannel) {
        this.fulfillmentChannel = fulfillmentChannel;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public int getQuantityUnshipped() {
        return quantityUnshipped;
    }

    public void setQuantityUnshipped(int quantityUnshipped) {
        this.quantityUnshipped = quantityUnshipped;
    }

    public int getQuantityShipped() {
        return quantityShipped;
    }

    public void setQuantityShipped(int quantityShipped) {
        this.quantityShipped = quantityShipped;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getMarketplaceListingId() {
        return marketplaceListingId;
    }

    public void setMarketplaceListingId(String marketplaceListingId) {
        this.marketplaceListingId = marketplaceListingId;
    }

    public String getMarketplaceOrderId() {
        return marketplaceOrderId;
    }

    public void setMarketplaceOrderId(String marketplaceOrderId) {
        this.marketplaceOrderId = marketplaceOrderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getMarketplaceSku() {
        return marketplaceSku;
    }

    public void setMarketplaceSku(String marketplaceSku) {
        this.marketplaceSku = marketplaceSku;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    
    public Date getShipByDate() {
        return shipByDate;
    }

    public void setShipByDate(Date shipByDate) {
        this.shipByDate = shipByDate;
    }

    public Date getDeliveryByDate() {
        return deliveryByDate;
    }

    public void setDeliveryByDate(Date deliveryByDate) {
        this.deliveryByDate = deliveryByDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNo() {
        return customerPhoneNo;
    }

    public void setCustomerPhoneNo(String customerPhoneNo) {
        this.customerPhoneNo = customerPhoneNo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public String getShipToName() {
        return shipToName;
    }

    public void setShipToName(String shipToName) {
        this.shipToName = shipToName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getShippingPhoneNo() {
        return shippingPhoneNo;
    }

    public void setShippingPhoneNo(String shippingPhoneNo) {
        this.shippingPhoneNo = shippingPhoneNo;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public MpnSkuMapping getMpnSkuMapping() {
        return mpnSkuMapping;
    }

    public void setMpnSkuMapping(MpnSkuMapping mpnSkuMapping) {
        this.mpnSkuMapping = mpnSkuMapping;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getWarehouseOrderNumber() {
        return warehouseOrderNumber;
    }

    public void setWarehouseOrderNumber(String warehouseOrderNumber) {
        this.warehouseOrderNumber = warehouseOrderNumber;
    }

    public String getShippingAddressLine1() {
        return shippingAddressLine1;
    }

    public void setShippingAddressLine1(String shippingAddressLine1) {
        this.shippingAddressLine1 = shippingAddressLine1;
    }

    public String getShippingAddressLine2() {
        return shippingAddressLine2;
    }

    public void setShippingAddressLine2(String shippingAddressLine2) {
        this.shippingAddressLine2 = shippingAddressLine2;
    }

    public String getShippingAddressLine3() {
        return shippingAddressLine3;
    }

    public void setShippingAddressLine3(String shippingAddressLine3) {
        this.shippingAddressLine3 = shippingAddressLine3;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", marketplaceOrderId=" + marketplaceOrderId + ", marketplaceSku=" + marketplaceSku + ", marketplaceListingId=" + marketplaceListingId + ", orderDate=" + orderDate + ", shipByDate=" + shipByDate + ", deliveryByDate=" + deliveryByDate + ", paymentDate=" + paymentDate + ", lastUpdatedDate=" + lastUpdatedDate + ", customerName=" + customerName + ", customerPhoneNo=" + customerPhoneNo + ", quantityUnshipped=" + quantityUnshipped + ", quantityShipped=" + quantityShipped + ", price=" + price + ", tax=" + tax + ", shipping=" + shipping + ", shipToName=" + shipToName + ", shippingAddressLine1=" + shippingAddressLine1 + ", shippingAddressLine2=" + shippingAddressLine2 + ", shippingAddressLine3=" + shippingAddressLine3 + ", city=" + city + ", state=" + state + ", postalCode=" + postalCode + ", country=" + country + ", shippingPhoneNo=" + shippingPhoneNo + ", orderStatus=" + orderStatus + ", fulfillmentChannel=" + fulfillmentChannel + ", customerId=" + customerId + ", processedBy=" + processedBy + ", processedDate=" + processedDate + ", marketplace=" + marketplace + ", manufacturer=" + manufacturer + ", warehouse=" + warehouse + ", trackingId=" + trackingId + ", trackingCarrier=" + trackingCarrier + ", trackingBy=" + trackingBy + ", trackingDate=" + trackingDate + ", shippingDate=" + shippingDate + ", mpnSkuMapping=" + mpnSkuMapping + ", orderItemId=" + orderItemId + ", warehouesIdeNum=" + warehouesIdeNum + ", priceCrntWHprct=" + priceCrntWHprct + ", poNumber=" + poNumber + ", warehouseOrderNumber=" + warehouseOrderNumber + ", updatedBy=" + updatedBy + '}';
    }

}
