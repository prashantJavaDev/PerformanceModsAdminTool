/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.ProductDetails;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Pallavi
 */
public class OrderDetailRowMapper implements RowMapper<ProductDetails> {

    @Override
    public ProductDetails mapRow(ResultSet rs, int i) throws SQLException {
        ProductDetails pd = new ProductDetails();
        pd.setWarehouseId(rs.getInt("WAREHOUSE_ID"));
        pd.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
        pd.setCurrentFeedDate(rs.getString("CURRENT_DATE"));
        pd.setCurrentPrice(rs.getDouble("CURRENT_PRICE"));
        pd.setCurrentQuantity(rs.getInt("CURRENT_QUANTITY"));
        pd.setImageUrl(rs.getString("LARGE_IMAGE_URL"));
        pd.setWarehouseMpn(rs.getString("WAREHOUSE_IDENTIFICATION_NO"));
        pd.setShipping(rs.getDouble("SHIPPING"));
        return pd;
    }
}
