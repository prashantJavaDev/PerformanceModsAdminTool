/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;


import com.aspirant.performanceModsAdminTool.model.ShippingCriteria;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class ShippingCriteriaRowMapper implements RowMapper<ShippingCriteria>{
    @Override
    public ShippingCriteria mapRow(ResultSet rs, int i) throws SQLException {
        ShippingCriteria shippingCriteria = new ShippingCriteria();
        shippingCriteria.setShippingCriteriaId(rs.getInt("SHIPPING_CRITERIA_ID"));
        shippingCriteria.setShippingCriteria(rs.getString("SHIPPING_CRITERIA"));
        return shippingCriteria;
    }
}