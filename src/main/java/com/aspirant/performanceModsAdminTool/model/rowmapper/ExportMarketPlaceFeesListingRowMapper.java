/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.Listing;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Pallavi
 */
public class ExportMarketPlaceFeesListingRowMapper implements RowMapper<Listing>{

    @Override
    public Listing mapRow(ResultSet rs, int i) throws SQLException {
        
            Listing listing = new Listing();
            listing.setMarketplaceListingId(rs.getString("MARKETPLACE_LISTING_ID"));
            listing.setSku(rs.getString("SKU"));
            listing.setCurrentPrice(rs.getDouble("CURRENT_PRICE"));
            listing.setCommission(rs.getDouble("CURRENT_COMMISSION"));
            return listing;
    }
    
    
    
}
