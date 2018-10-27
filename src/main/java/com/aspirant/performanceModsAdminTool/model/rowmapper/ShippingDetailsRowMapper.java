/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.ShippingCriteria;
import com.aspirant.performanceModsAdminTool.model.ShippingDetails;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class ShippingDetailsRowMapper implements RowMapper<ShippingDetails> {

    @Override
    public ShippingDetails mapRow(ResultSet rs, int i) throws SQLException {
        ShippingDetails shippingDetails = new ShippingDetails();
        shippingDetails.setFlatRateValue(rs.getDouble("FLAT_RATE_VALUE"));
        shippingDetails.setMaxValuePrice(rs.getDouble("MAX_VALUE_PRICE"));
        shippingDetails.setMaxValuePriceShipping(rs.getDouble("MAX_VALUE_PRICE_SHIPPING"));
        shippingDetails.setMaxValueWeight(rs.getDouble("MAX_VALUE_WEIGHT"));
        shippingDetails.setMaxValueWeightShipping(rs.getDouble("MAX_VALUE_WEIGHT_SHIPPING"));
        shippingDetails.setMinValuePrice(rs.getDouble("MIN_VALUE_PRICE"));
        shippingDetails.setMinValuePriceShipping(rs.getDouble("MIN_VALUE_PRICE_SHIPPING"));
        shippingDetails.setMinValueWeight(rs.getDouble("MIN_VALUE_WEIGHT"));
        shippingDetails.setMinValueWeightShipping(rs.getDouble("MIN_VALUE_WEIGHT_SHIPPING"));
        
        ShippingCriteria shippingCriteria = new ShippingCriteria();
        shippingCriteria.setShippingCriteriaId(rs.getInt("SHIPPING_CRITERIA_ID"));
        shippingCriteria.setShippingCriteria(rs.getString("SHIPPING_CRITERIA"));
        
        Warehouse w = new Warehouse();
        w.setWarehouseId(rs.getString("WAREHOUSE_ID"));
                
        return shippingDetails;
    }
}
