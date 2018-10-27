/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.Listing;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class ListingRowMapper implements RowMapper<Listing> {

    @Override
    public Listing mapRow(ResultSet rs, int i) throws SQLException {
        Listing l = new Listing();
        //l.setCommission(rs.getDouble("CURRENT_COMMISSION"));
        l.setIsAmazonFulfilled(rs.getBoolean("ISAMAZONFULLFILLED"));
        l.setLastListedPrice(rs.getDouble("LAST_LISTED_PRICE"));
        //l.setMarketplaceListingId(rs.getString("MARKETPLACE_LISTING_ID"));
        l.setPack(rs.getInt("PACK"));
        l.setSku(rs.getString("SKU"));
        l.setCurrentQunatity(rs.getInt("CURRENT_QUANTITY"));
        return l;
    }
}
