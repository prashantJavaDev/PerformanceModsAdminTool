/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.Order;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Pallavi
 */
public class OrderRequestXMLRowMapper implements RowMapper<Order>{

    @Override
    public Order mapRow(ResultSet rs, int i) throws SQLException {
        Order order = new Order();
                    order.setPoNumber(rs.getString("PO_NUMBER"));
                    order.setCustomerName(rs.getString("SHIP_TO_NAME"));
                    order.setShippingAddressLine1(rs.getString("SHIPPING_ADDRESS_LINE1"));
                    order.setShippingAddressLine2(rs.getString("SHIPPING_ADDRESS_LINE2"));
                    order.setShippingAddressLine3(rs.getString("SHIPPING_ADDRESS_LINE3"));
                    order.setCity(rs.getString("CITY"));
                    order.setState(rs.getString("STATE"));
                    order.setPostalCode(rs.getString("POSTAL_CODE"));
                    order.setWarehouesIdeNum(rs.getString("WAREHOUSE_IDENTIFICATION_NO"));
                    order.setPriceCrntWHprct(rs.getDouble("PRICE"));
                    order.setQuantityUnshipped(rs.getInt("QUANTITY_UNSHIPPED"));
                    return order;
    }
    
}
