/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.DTO.mapper;

import com.aspirant.performanceModsAdminTool.model.DTO.ProductListingDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class ProductListingDTORowMapper implements RowMapper<ProductListingDTO> {
    
    @Override
    public ProductListingDTO mapRow(ResultSet rs, int i) throws SQLException {
        
        ProductListingDTO productListingDTO = new ProductListingDTO();
        productListingDTO.setSku(rs.getString("SKU"));
        productListingDTO.setListingId(rs.getString("MARKETPLACE_LISTING_ID"));
        productListingDTO.setListedPrice(rs.getDouble("LAST_LISTED_PRICE"));
        productListingDTO.setListedQuantity(rs.getInt("LAST_LISTED_QUANTITY"));
        productListingDTO.setLastListedDate(rs.getTimestamp("LAST_LISTED_DATE"));
        productListingDTO.setCurrentPrice(rs.getDouble("CURRENT_PRICE"));
        productListingDTO.setCurrentQuantity(rs.getInt("CURRENT_QUANTITY"));
        productListingDTO.setCurrentListedDate(rs.getTimestamp("CURRENT_LISTED_DATE"));
        productListingDTO.setWarehouse(rs.getString("WAREHOUSE_NAME"));
        productListingDTO.setActive(rs.getBoolean("ACTIVE"));
        productListingDTO.setWarehouseId(rs.getInt("WAREHOUSE_ID"));
        productListingDTO.setManufacturerMpn(rs.getString("MANUFACTURER_MPN"));
        productListingDTO.setManufacturerName(rs.getString("MANUFACTURER_NAME"));
        productListingDTO.setShipping(rs.getDouble("CURRENT_SHIPPING"));
        productListingDTO.setCommission(rs.getDouble("CURRENT_COMMISSION"));
        productListingDTO.setProfit(rs.getDouble("CURRENT_PROFIT"));
        productListingDTO.setProfitPercentage(rs.getInt("CURRENT_PROFIT_PERCENTAGE"));
        productListingDTO.setCommissionPercentage(rs.getInt("CURRENT_COMMISSION_PERCENTAGE"));
        productListingDTO.setWarehouseQuantity(rs.getInt("QUANTITY"));
        productListingDTO.setWarehousePrice(rs.getDouble("PRICE"));
        productListingDTO.setPack(rs.getInt("PACK"));
        
        return productListingDTO;
    }
}
