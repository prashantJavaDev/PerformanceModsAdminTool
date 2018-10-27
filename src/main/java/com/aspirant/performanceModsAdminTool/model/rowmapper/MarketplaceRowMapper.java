/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.Marketplace;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author shrutika
 */
public class MarketplaceRowMapper implements RowMapper<Marketplace> {

    @Override
    public Marketplace mapRow(ResultSet rs, int i) throws SQLException {
        Marketplace m = new Marketplace();
        m.setMarketplaceId(rs.getInt("MARKETPLACE_ID"));
        m.setMarketplaceName(rs.getString("MARKETPLACE_NAME"));
        m.setActive(rs.getBoolean("ACTIVE"));

        return m;
    }
}
