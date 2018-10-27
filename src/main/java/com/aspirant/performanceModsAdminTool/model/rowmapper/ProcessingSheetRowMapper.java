/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.ProcessingSheet;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class ProcessingSheetRowMapper implements RowMapper<ProcessingSheet> {

    @Override
    public ProcessingSheet mapRow(ResultSet rs, int i) throws SQLException {
        ProcessingSheet ps = new ProcessingSheet();
        //ps.setPoNumber(rs.getInt("PO_NUMBER"));
        ps.setWarehouseId(rs.getInt("WAREHOUSE_ID"));
        ps.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
        ps.setManufacturerName(rs.getString("MANUFACTURER_NAME"));
        ps.setMPN(rs.getString("WAREHOUSE_MPN"));
        ps.setQuantity(rs.getInt("QUANTITY_SHIPPED"));
        ps.setPrice(rs.getDouble("PRICE"));
        ps.setShipToName(rs.getString("SHIP_TO_NAME"));
        ps.setShippingAddressLine1(rs.getString("SHIPPING_ADDRESS_LINE1"));
        ps.setShippingAddressLine2(rs.getString("SHIPPING_ADDRESS_LINE2"));
        ps.setShippingAddressLine3(rs.getString("SHIPPING_ADDRESS_LINE3"));
        ps.setCity(rs.getString("CITY"));
        ps.setState(rs.getString("STATE"));
        ps.setZip(rs.getString("POSTAL_CODE"));
        ps.setPhoneNumber(rs.getString("SHIPPING_PHONE_NO"));

        return ps;
    }
}
