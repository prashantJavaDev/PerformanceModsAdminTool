/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.TicketStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class TicketStatusRowMapper implements RowMapper<TicketStatus> {

    @Override
    public TicketStatus mapRow(ResultSet rs, int i) throws SQLException {
        TicketStatus t = new TicketStatus();
        t.setStatusId(rs.getInt("STATUS_ID"));
        t.setStatusDesc(rs.getString("STATUS_DESC"));
        t.setActive(rs.getBoolean("ACTIVE"));

        return t;
    }

}
