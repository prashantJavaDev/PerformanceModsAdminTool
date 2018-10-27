/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.User;
import com.aspirant.performanceModsAdminTool.model.Warehouse;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author shrutika
 */
public class WarehouseRowMapper implements RowMapper<Warehouse> {

    @Override
    public Warehouse mapRow(ResultSet rs, int i) throws SQLException {
        Warehouse w = new Warehouse();
        w.setWarehouseId(rs.getString("WAREHOUSE_ID"));
        w.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
        w.setWarehouseAddress(rs.getString("WAREHOUSE_ADDRESS"));
        w.setWarehousePhone(rs.getString("WAREHOUSE_PHONE"));
        w.setWarehouseRepName(rs.getString("WAREHOUSE_REP_NAME"));
        w.setWarehouseRepEmail(rs.getString("WAREHOUSE_REP_EMAIL"));
        w.setWarehouseCustServiceEmail(rs.getString("WAREHOUSE_CUST_SER_EMAIL"));
        w.setActive(rs.getBoolean("ACTIVE"));

        return w;
    }
}
