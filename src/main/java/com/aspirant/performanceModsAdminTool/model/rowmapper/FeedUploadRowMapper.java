/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.FeedUpload;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author shrutika
 */
public class FeedUploadRowMapper implements RowMapper<FeedUpload> {

    @Override
    public FeedUpload mapRow(ResultSet rs, int i) throws SQLException {
        FeedUpload f = new FeedUpload();
        f.setLineNo(rs.getInt("LINE_NO"));
        f.setManufacturerName(rs.getString("MANUFACTURER_NAME"));
        f.setProductMpn(rs.getString("MPN"));
        f.setProductMap(rs.getString("MAP"));
        f.setProductMsrp(rs.getString("MSRP"));
        f.setProductPrice(rs.getString("PRICE"));
        f.setProductQuantity(rs.getString("QUANTITY"));
        f.setProductCondition(rs.getString("CONDITION"));
        f.setWarehouseIdentificationNo(rs.getString("WAREHOUSE_IDENTIFICATION_NO"));
        f.setProductWeight(rs.getString("WEIGHT"));
        f.setEstimatedShipWeight(rs.getString("ESTIMATED_SHIP_WEIGHT"));
        f.setProductLength(rs.getString("LENGTH"));
        f.setProductWidth(rs.getString("WIDTH"));
        f.setProductHeight(rs.getString("HEIGHT"));
        f.setUpc(rs.getString("UPC"));
        f.setReason(rs.getString("REASON"));
        f.setStatus(rs.getBoolean("STATUS"));
        f.setProductStatus(rs.getInt("PRODUCT_STATUS"));
        f.setResizeImageUrl(rs.getString("RESIZE_IMAGE_URL"));
        f.setLargeImageUrl(rs.getString("LARGE_IMAGE_URL"));
        f.setShipping(rs.getString("SHIPPING"));

        return f;
    }
}
