/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.Company;
import com.aspirant.performanceModsAdminTool.model.Marketplace;
import com.aspirant.performanceModsAdminTool.model.Ticket;
import com.aspirant.performanceModsAdminTool.model.TicketPriority;
import com.aspirant.performanceModsAdminTool.model.TicketStatus;
import com.aspirant.performanceModsAdminTool.model.TicketType;
import com.aspirant.performanceModsAdminTool.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class TicketRowMapper implements RowMapper<Ticket> {

    @Override
    public Ticket mapRow(ResultSet rs, int i) throws SQLException {
        Ticket t = new Ticket();
        t.setTicketId(rs.getInt("TICKET_ID"));
        t.setTicketNo(rs.getString("TICKET_NO"));

        Marketplace m = new Marketplace();
        m.setMarketplaceName(rs.getString("MARKETPLACE_NAME"));
        t.setMarketplace(m);

        TicketType tt = new TicketType();
        tt.setTicketTypeId(rs.getInt("TICKET_TYPE_ID"));
        tt.setTicketTypeDesc(rs.getString("TICKET_TYPE_DESC"));
        t.setTicketType(tt);

        TicketPriority tp = new TicketPriority();
        tp.setPriorityName(rs.getString("PRIORITY_NAME"));
        t.setTicketPriority(tp);

        t.setOrderId(rs.getString("ORDER_ID"));
        t.setTrackingId(rs.getString("TRACKING_ID"));
        t.setTrackingCarrierName(rs.getString("TRACKING_CARRIER_NAME"));
        t.setDescription(rs.getString("DESCRIPTION"));
        t.setDetails(rs.getString("DETAILS"));

        Company c = new Company();
        c.setCompanyName(rs.getString("COMPANY_NAME"));
        t.setCompany(c);

        t.setCustomerName(rs.getString("CUST_NAME"));
        t.setCustPhoneNumber(rs.getString("CUST_PHONE_NO"));
        t.setCustEmailId(rs.getString("CUST_EMAIL_ID"));

        User asn = new User();
        asn.setUserId(rs.getInt("ASSIGNED_TO"));
        asn.setUsername(rs.getString("USERNAME"));
        t.setAssignedTo(asn);

        t.setAssignedOn(rs.getTimestamp("ASSIGNED_ON"));

        TicketStatus ts = new TicketStatus();
        ts.setStatusId(rs.getInt("TICKET_STATUS_ID"));
        ts.setStatusDesc(rs.getString("STATUS_DESC"));
        t.setTicketStatus(ts);

        t.setCompletedOn(rs.getTimestamp("COMPLETED_ON"));

        User mod = new User();
        mod.setUserId(rs.getInt("LAST_MODIFIED_BY"));
        t.setLastModifiedBy(mod);
        
        User crBy = new User();
        crBy.setUsername(rs.getString("CREATED_BY_NAME"));
        crBy.setUserId(rs.getInt("CREATED_BY"));
        t.setCreatedBy(crBy);
        
        
        t.setCreateDate(rs.getTimestamp("CREATED_DATE"));
        //t.getDescription(rs.getString("DESCRIPTION"));
        //t.getDetails(rs.getString("DETAILS"));
        return t;
    }
}
