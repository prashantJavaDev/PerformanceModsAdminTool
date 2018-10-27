/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.Marketplace;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.User;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Pallavi
 */
public class MarketplaceOrderDetailsRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int i) throws SQLException {
        Order o = new Order();
        o.setPoNumber(rs.getString("PO_NUMBER"));
        o.setTax(rs.getDouble("TAX"));
        o.setMarketplaceOrderId(rs.getString("MARKETPLACE_ORDER_ID"));
        Marketplace mp = new Marketplace();
        mp.setMarketplaceName(rs.getString("MARKETPLACE_NAME"));
        o.setMarketplace(mp);
        o.setMarketplaceSku(rs.getString("MARKETPLACE_SKU"));
        o.setMarketplaceListingId(rs.getString("MARKETPLACE_LISTING_ID"));
        o.setOrderDate(rs.getTimestamp("ORDER_DATE"));
        o.setShipByDate(rs.getTimestamp("SHIP_BY_DATE"));
        o.setDeliveryByDate(rs.getTimestamp("DELIVERY_BY_DATE"));
        o.setLastUpdatedDate(rs.getTimestamp("LAST_UPDATED_DATE"));
        o.setCustomerName(rs.getString("CUSTOMER_NAME"));
        o.setCustomerPhoneNo(rs.getString("CUSTOMER_PHONE_NO"));
        o.setQuantityUnshipped(rs.getInt("QUANTITY_UNSHIPPED"));
        o.setQuantityShipped(rs.getInt("QUANTITY_SHIPPED"));
        o.setPrice(rs.getDouble("PRICE"));
        o.setShipToName(rs.getString("SHIP_TO_NAME"));
        o.setShippingAddressLine1(rs.getString("SHIPPING_ADDRESS_LINE1"));
        o.setShippingAddressLine2(rs.getString("SHIPPING_ADDRESS_LINE2"));
        o.setShippingAddressLine3(rs.getString("SHIPPING_ADDRESS_LINE3"));
        o.setCity(rs.getString("CITY"));
        o.setState(rs.getString("STATE"));
        o.setPostalCode(rs.getString("POSTAL_CODE"));
        o.setCountry(rs.getString("COUNTRY"));
        o.setShippingPhoneNo(rs.getString("SHIPPING_PHONE_NO"));
        o.setOrderStatus(rs.getString("ORDER_STATUS"));
        o.setFulfillmentChannel(rs.getString("FULFILLMENT_CHANNEL"));
        o.setTrackingId(rs.getString("TRACKING_ID"));
        o.setTrackingCarrier(rs.getString("TRACKING_CARRIER"));
        Warehouse w = new Warehouse();
        w.setWarehouseId(rs.getString("WAREHOUSE_ID"));
        w.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
        o.setWarehouse(w);
        o.setTrackingDate(rs.getTimestamp("TRACKING_DATE"));
        o.setProcessedDate(rs.getTimestamp("PROCESSED_DATE"));

        User usr1 = new User();
        usr1.setUsername(rs.getString("pby"));
        o.setProcessedBy(usr1);

        User usr = new User();
        usr.setUsername(rs.getString("tby"));
        o.setTrackingBy(usr);
        o.setUpdatedBy(rs.getString("UPDATED_BY"));
        return o;
    }
    
}
