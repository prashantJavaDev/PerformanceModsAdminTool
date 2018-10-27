/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.TicketPriority;
import com.aspirant.performanceModsAdminTool.model.TicketType;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class TicketTypeRowMapper implements RowMapper<TicketType> {

    @Override
    public TicketType mapRow(ResultSet rs, int i) throws SQLException {
        TicketType t = new TicketType();
        t.setTicketTypeId(rs.getInt("TICKET_TYPE_ID"));
        t.setTicketTypeDesc(rs.getString("TICKET_TYPE_DESC"));
        t.setTicketCode(rs.getString("TICKET_CODE"));
        t.setActive(rs.getBoolean("ACTIVE"));
        
        TicketPriority tp = new TicketPriority();
        tp.setPriorityId(rs.getInt("PRIORITY_ID"));
        t.setPriority(tp);

        return t;
    }
}
