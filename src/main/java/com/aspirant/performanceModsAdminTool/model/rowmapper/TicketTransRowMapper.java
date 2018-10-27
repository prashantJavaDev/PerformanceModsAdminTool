/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.TicketStatus;
import com.aspirant.performanceModsAdminTool.model.TicketTrans;
import com.aspirant.performanceModsAdminTool.model.TicketType;
import com.aspirant.performanceModsAdminTool.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class TicketTransRowMapper implements RowMapper<TicketTrans> {

    @Override
    public TicketTrans mapRow(ResultSet rs, int i) throws SQLException {
        TicketTrans t = new TicketTrans();
        t.setTicketId(rs.getInt("TICKET_ID"));
        t.setTransDate(rs.getTimestamp("TRANS_DATE"));
        t.setTrackingId(rs.getString("TRACKING_ID"));
        t.setTrackingCarrierName(rs.getString("TRACKING_CARRIER_NAME"));
        t.setNotes(rs.getString("NOTES"));

        User asn = new User();
        asn.setUsername(rs.getString("USERNAME"));
        t.setAssignedTo(asn);

        TicketStatus ts = new TicketStatus();
        ts.setStatusId(rs.getInt("TICKET_STATUS_ID"));
        ts.setStatusDesc(rs.getString("STATUS_DESC"));
        t.setTicketStatus(ts);
        
        TicketType tt=new TicketType();
        tt.setTicketTypeId(rs.getInt("TICKET_TYPE_ID"));
        tt.setTicketTypeDesc(rs.getString("TICKET_TYPE_DESC"));
        t.setTicketType(tt);
        
        t.setPriorityName(rs.getString("PRIORITY_NAME"));
        
        User usr = new User();
        usr.setUsername(rs.getString("LAST_MODIFIED_BY"));
        t.setLastModifiedBy(usr);

        return t;
    }
}
