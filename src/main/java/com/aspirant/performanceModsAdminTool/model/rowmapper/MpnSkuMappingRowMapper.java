/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.MpnSkuMapping;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class MpnSkuMappingRowMapper implements RowMapper<Object>{

    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        MpnSkuMapping mapping = new MpnSkuMapping();
        mapping.setManufacturerMPN(rs.getString("MANUFACTURER_MPN"));
        return mapping;
        
    }
    
}
