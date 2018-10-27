
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.DTO.mapper;

import com.aspirant.performanceModsAdminTool.model.DTO.MarketplaceListingSkuDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class MarketplaceListingSkuDTORowMapper implements RowMapper<MarketplaceListingSkuDTO> {

    @Override
    public MarketplaceListingSkuDTO mapRow(ResultSet rs, int i) throws SQLException {

        MarketplaceListingSkuDTO marketplaceListingSkuDTO = new MarketplaceListingSkuDTO();
        marketplaceListingSkuDTO.setMarketplaceName(rs.getString("MARKETPLACE_NAME"));
        marketplaceListingSkuDTO.setListingId(rs.getString("MARKETPLACE_LISTING_ID"));
        marketplaceListingSkuDTO.setMarketplaceSku(rs.getString("SKU"));
        marketplaceListingSkuDTO.setCurrentListedDate(rs.getTimestamp("CURRENT_LISTED_DATE"));
        marketplaceListingSkuDTO.setCurrentPrice(rs.getString("CURRENT_PRICE"));
        marketplaceListingSkuDTO.setCurrentQuantity(rs.getString("CURRENT_QUANTITY"));
        marketplaceListingSkuDTO.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
        return marketplaceListingSkuDTO;
    }
}
