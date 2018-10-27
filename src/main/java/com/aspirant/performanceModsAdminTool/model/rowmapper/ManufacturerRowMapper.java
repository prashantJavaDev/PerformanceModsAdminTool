/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.Manufacturer;
import com.aspirant.performanceModsAdminTool.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author shrutika
 */
public class ManufacturerRowMapper implements RowMapper<Manufacturer> {

    @Override
    public Manufacturer mapRow(ResultSet rs, int i) throws SQLException {
        Manufacturer m = new Manufacturer();
        m.setManufacturerId(rs.getInt("MANUFACTURER_ID"));
        m.setManufacturerName(rs.getString("MANUFACTURER_NAME"));
        m.setManufacturerCode(rs.getString("MANUFACTURER_CODE"));

        return m;
    }
}