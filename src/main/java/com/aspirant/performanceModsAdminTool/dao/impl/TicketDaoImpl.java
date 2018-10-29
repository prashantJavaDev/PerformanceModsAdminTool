/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.EmailerDao;
import com.aspirant.performanceModsAdminTool.dao.TicketDao;
import com.aspirant.performanceModsAdminTool.dao.UserDao;
import com.aspirant.performanceModsAdminTool.framework.ApplicationSession;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.DTO.TicketFilterDTO;
import com.aspirant.performanceModsAdminTool.model.Email;
import com.aspirant.performanceModsAdminTool.model.EmailTemplate;
import com.aspirant.performanceModsAdminTool.model.Marketplace;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.Ticket;
import com.aspirant.performanceModsAdminTool.model.TicketStatus;
import com.aspirant.performanceModsAdminTool.model.TicketTrans;
import com.aspirant.performanceModsAdminTool.model.TicketType;
import com.aspirant.performanceModsAdminTool.model.User;
import com.aspirant.performanceModsAdminTool.model.rowmapper.AssignedToRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.MarketplaceOrderRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.TicketRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.TicketStatusRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.TicketTransRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.TicketTypeRowMapper;
import com.aspirant.performanceModsAdminTool.service.EmailerService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ritesh
 */
@Repository
public class TicketDaoImpl implements TicketDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Autowired
    private EmailerDao emailerDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private EmailerService emailerService;

    @Override
    public List<TicketType> getTicketTypeList() {
        String sql = "SELECT * FROM tkt_ticket_type ttt ORDER BY ttt.`TICKET_TYPE_ID`";
        return this.jdbcTemplate.query(sql, new TicketTypeRowMapper());
    }

    @Override
    public List<TicketStatus> getTicketStatusList(int currentStatusId) {
        String sql = "SELECT * FROM tkt_ticket_status tts \n"
                + "LEFT JOIN tkt_status_matrix tsm ON tsm.`EXPECTED_STATUS_ID`=tts.`STATUS_ID`\n"
                + "WHERE tsm.`CURRENT_STATUS_ID`=? ORDER BY tts.`STATUS_ID`";
        return this.jdbcTemplate.query(sql, new TicketStatusRowMapper(), currentStatusId);
    }

    @Override
    public List<User> getAssignedToList(boolean active) {
        String sql = "SELECT u.`USER_ID`,u.`USERNAME` FROM `user` u\n"
                + "LEFT JOIN `user_role`ur  ON ur.`USER_ID`=u.`USER_ID`\n"
                + "WHERE ur.`ROLE_ID` IN ('ROLE_AGENT','ROLE_SUPERVISOR','ROLE_MANAGER')";

        if (active) {
            sql += " AND u.`ACTIVE`";
        }
        sql += "ORDER BY u.`USERNAME`";
        return this.jdbcTemplate.query(sql, new AssignedToRowMapper());
    }

    @Override
    @Transactional
    public Ticket createNewTicket(Ticket ticket) {
        CustomUserDetails curUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
        ApplicationSession as = ApplicationSession.getCurrent();
        as.reloadTicketTypeList();
        TicketType t = as.getTicketType(ticket.getTicketType());
        ticket.setCreateDate(curDate);
        ticket.setLastModifiedDate(curDate);
        User user = new User();
        user.setUserId(curUser.getUserId());
        ticket.setCreatedBy(user);
        ticket.setLastModifiedBy(user);

        SimpleJdbcInsert sji = new SimpleJdbcInsert(dataSource).withTableName("tkt_ticket").usingGeneratedKeyColumns("TICKET_ID");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("MARKETPLACE_ID", ticket.getMarketplace().getMarketplaceId());
        params.put("TICKET_TYPE_ID", ticket.getTicketType().getTicketTypeId());
        params.put("TICKET_PRIORITY_ID", t.getPriority().getPriorityId());
        params.put("ORDER_ID", ticket.getOrderId());
        if (ticket.getTicketType().getTicketTypeId() == 3) {
            params.put("TRACKING_ID", ticket.getTrackingId());
            params.put("TRACKING_CARRIER_NAME", ticket.getTrackingCarrierName());
        }
        params.put("DESCRIPTION", ticket.getDescription());
        params.put("DETAILS", ticket.getDetails());
        params.put("COMPANY_ID", ticket.getCompany().getCompanyId());
        params.put("CUST_NAME", ticket.getCustomerName());
        params.put("CUST_PHONE_NO", ticket.getCustPhoneNumber());
        params.put("CUST_EMAIL_ID", ticket.getCustEmailId());
        //if status assigned or complated
        if (ticket.getTicketStatus().getStatusId() != 1) {
            ticket.setAssignedOn(curDate);
            params.put("ASSIGNED_TO", ticket.getAssignedTo().getUserId());
            params.put("ASSIGNED_ON", curDate);

            //if status complated
            if (ticket.getTicketStatus().getStatusId() == 4) {
                ticket.setCompletedOn(curDate);
                params.put("COMPLETED_ON", curDate);
            }
        }
        params.put("TICKET_STATUS_ID", ticket.getTicketStatus().getStatusId());
        params.put("LEVEL", 1);
        params.put("CREATEd_DATE", ticket.getCreateDate());
        params.put("LAST_MODIFIED_DATE", ticket.getLastModifiedDate());
        params.put("CREATED_BY", ticket.getCreatedBy().getUserId());
        params.put("LAST_MODIFIED_BY", ticket.getLastModifiedBy().getUserId());
        String ticketCode;
        do {
            ticketCode = t.getTicketCode() + "-" + RandomStringUtils.randomAlphanumeric(6).toUpperCase();
        } while (checkTicketCodeExists(ticketCode));

        ticket.setTicketNo(ticketCode);
        String ticketNo = ticket.getTicketNo();
        params.put("TICKET_NO", ticketNo);
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Insert into ticket : " + params, GlobalConstants.TAG_SYSTEMLOG));
        int ticketId = sji.executeAndReturnKey(params).intValue();
        params.clear();

        ticket.setTicketId(ticketId);

        //Insert in to Task History
        SimpleJdbcInsert sji1 = new SimpleJdbcInsert(dataSource).withTableName("tkt_ticket_trans").usingGeneratedKeyColumns("TRANS_ID");
        params.put("TRANS_DATE", curDate);
        params.put("TICKET_ID", ticket.getTicketId());
        if (ticket.getTicketStatus().getStatusId() != 1) {
            params.put("ASSIGNED_TO", ticket.getAssignedTo().getUserId());
            params.put("ASSIGNED_ON", curDate);
            //if status complated
            if (ticket.getTicketStatus().getStatusId() == 4) {
                params.put("COMPLETED_ON", curDate);
            }
        }
        params.put("TICKET_STATUS_ID", ticket.getTicketStatus().getStatusId());
        if (ticket.getTicketType().getTicketTypeId() == 3) {
            params.put("TRACKING_ID", ticket.getTrackingId());
            params.put("TRACKING_CARRIER_NAME", ticket.getTrackingCarrierName());
        }
        params.put("TICKET_TYPE_ID", ticket.getTicketType().getTicketTypeId());
        params.put("PRIORITY_ID", t.getPriority().getPriorityId());
        params.put("LEVEL", 1);
        params.put("NOTES", ticket.getNotes());
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser.getUserId());

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Insert into ticketTrans : " + params, GlobalConstants.TAG_SYSTEMLOG));
        int ticketTransId = sji1.executeAndReturnKey(params).intValue();
        params.clear();
        return ticket;
    }

    private boolean checkTicketCodeExists(String ticketCode) {
        String sql = " SELECT COUNT(*) AS totalCount FROM tkt_ticket tt WHERE tt.`TICKET_NO`=? ; ";
        int code = this.jdbcTemplate.queryForObject(sql, Integer.class, ticketCode);
        if (code > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> searchCustomerName(String term) {
        String sql = "SELECT DISTINCT(t.`CUST_NAME`) FROM tkt_ticket  t WHERE t.`CUST_NAME` LIKE '%" + term + "%'";

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        return list;
    }

    @Override
    public List<String> searchTicketNo(String term) {
        String sql = "SELECT t.`TICKET_NO` FROM tkt_ticket  t WHERE t.`TICKET_NO` LIKE '%" + term + "%'";

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        return list;
    }

    @Override
    public List<Ticket> getAllTicketList(String startDate, String stopDate, TicketFilterDTO ticketFilterDTO, int viewType) {
        CustomUserDetails curUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT tt.*,tm.`MARKETPLACE_NAME`,ttt.`TICKET_TYPE_DESC`,tp.`PRIORITY_NAME`,\n"
                + "tc.`COMPANY_NAME`,asn.`USERNAME`,tts.`STATUS_DESC`,crBy.`USERNAME` CREATED_BY_NAME FROM tkt_ticket tt \n"
                + "LEFT JOIN pm_marketplace tm ON tm.`MARKETPLACE_ID`=tt.`MARKETPLACE_ID`\n"
                + "LEFT JOIN tkt_ticket_type ttt ON ttt.`TICKET_TYPE_ID`=tt.`TICKET_TYPE_ID`\n"
                + "LEFT JOIN tkt_priority tp ON tp.`PRIORITY_ID`=tt.`TICKET_PRIORITY_ID`\n"
                + "LEFT JOIN tkt_company tc ON tc.`COMPANY_ID`=tt.`COMPANY_ID`\n"
                + "LEFT JOIN `user` asn ON asn.`USER_ID`=tt.`ASSIGNED_TO`\n"
                + "LEFT JOIN `user` crBy ON crBy.`USER_ID`=tt.`CREATED_BY`\n"
                + "LEFT JOIN tkt_ticket_status tts ON tts.`STATUS_ID`=tt.`TICKET_STATUS_ID`\n"
                + "LEFT JOIN tkt_status_matrix tsm ON tsm.`EXPECTED_STATUS_ID`=tts.`STATUS_ID`\n"
                + "WHERE tsm.`CURRENT_STATUS_ID`=:statusMatrixId");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("statusMatrixId", ticketFilterDTO.getStatusMatrixId());

        if (startDate != null && stopDate != null) {
            startDate += " 00:00:00";
            stopDate += " 23:59:59";
            sql.append(" AND tt.`CREATED_DATE` BETWEEN :startDate AND :stopDate");
            params.put("startDate", startDate);
            params.put("stopDate", stopDate);
        }

        if (ticketFilterDTO.getMarketplaceId() != 0) {
            sql.append(" AND tt.MARKETPLACE_ID=:marketplaceId ");
            params.put("marketplaceId", ticketFilterDTO.getMarketplaceId());
        }
        if (ticketFilterDTO.getTicketTypeId() != 0) {
            sql.append(" AND tt.TICKET_TYPE_ID=:ticketTypeId ");
            params.put("ticketTypeId", ticketFilterDTO.getTicketTypeId());
        }
        if (ticketFilterDTO.getCompanyId() != 0) {
            sql.append(" AND tt.COMPANY_ID=:companyId ");
            params.put("companyId", ticketFilterDTO.getCompanyId());
        }
        if (ticketFilterDTO.getAssignedToId() != 0) {
            sql.append(" AND tt.ASSIGNED_TO=:assignedToId ");
            params.put("assignedToId", ticketFilterDTO.getAssignedToId());
        }
        if (ticketFilterDTO.getTicketStatusId() != 0) {
            sql.append(" AND tt.TICKET_STATUS_ID=:ticketStatusId ");
            params.put("ticketStatusId", ticketFilterDTO.getTicketStatusId());
        }

        if (ticketFilterDTO.getOrderId() != null && ticketFilterDTO.getOrderId() != "") {
            sql.append(" AND tt.ORDER_ID=:orderId");
            params.put("orderId", ticketFilterDTO.getOrderId());
        }

        if (ticketFilterDTO.getCustomerName() != null && ticketFilterDTO.getCustomerName() != "") {
            sql.append(" AND tt.CUST_NAME=:customerName");
            params.put("customerName", ticketFilterDTO.getCustomerName());
        }

        if (ticketFilterDTO.getTicketNo() != null && !ticketFilterDTO.getTicketNo().equals("")) {
            sql.append(" AND tt.TICKET_NO=:ticketNo");
            params.put("ticketNo", ticketFilterDTO.getTicketNo());
        }

        if (ticketFilterDTO.getRead() != 0) {
            if (ticketFilterDTO.getRead() == 1) {
                sql.append(" AND tt.`LAST_MODIFIED_BY`=:lastModifiedBy AND tt.`TICKET_STATUS_ID`!=1");
            } else {
                sql.append(" AND (tt.`LAST_MODIFIED_BY`!=:lastModifiedBy OR  tt.`TICKET_STATUS_ID`=1)");
            }
            params.put("lastModifiedBy", curUser.getUserId());
        }

        if (viewType == 1) {
            sql.append(" AND (tt.`ASSIGNED_TO`=:curUser OR tt.`CREATED_BY`=:curUser)");
            params.put("curUser", curUser.getUserId());
        }

        sql.append(" ORDER BY tt.`LAST_MODIFIED_DATE` DESC");

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), params, GlobalConstants.TAG_SYSTEMLOG));
        return nm.query(sql.toString(), params, new TicketRowMapper());
    }

    @Override
    @Transactional
    public void reopenTicket(int ticketId, int ticketTypeId, String notes) {
        CustomUserDetails curUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
        TicketType tt = new TicketType();
        tt.setTicketTypeId(ticketTypeId);
        String newNotes = "Ticket is reopen due to : " + notes;
        ApplicationSession as = ApplicationSession.getCurrent();
        TicketType t = as.getTicketType(tt);

        Map<String, Object> map = new HashMap<String, Object>();
        String sql = "UPDATE tkt_ticket tt SET tt.`ASSIGNED_TO`=NULL, tt.`ASSIGNED_ON`=NULL,\n"
                + "tt.`TICKET_STATUS_ID`=1 ,tt.`COMPLETED_ON`=NULL,tt.TICKET_TYPE_ID=:ticketTypeId,\n"
                + "tt.TICKET_PRIORITY_ID=:priorityId,tt.`LAST_MODIFIED_BY`=:lastModifiedBy, tt.`LAST_MODIFIED_DATE`=:lastModifiedDate, tt.NOTES=:notes \n"
                + "WHERE tt.`TICKET_ID`=:ticketId";

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(dataSource);

        map.put("lastModifiedBy", curUser.getUserId());
        map.put("lastModifiedDate", curDate);
        map.put("notes", newNotes);
        map.put("ticketId", ticketId);
        map.put("ticketTypeId", ticketTypeId);
        map.put("priorityId", t.getPriority().getPriorityId());

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, map, GlobalConstants.TAG_SYSTEMLOG));
        int id = nm.update(sql, map);

        map.clear();

        sql = "INSERT INTO tkt_ticket_trans\n"
                + " SELECT NULL,NOW(),tt.`TICKET_ID`,NULL,NULL,1,NULL,tt.`TRACKING_ID`,\n"
                + " tt.`TRACKING_CARRIER_NAME`,:ticketTypeId,:priorityId,tt.`LEVEL`,:notes,:lastModifiedBy,NOW()"
                + " FROM tkt_ticket tt\n"
                + " WHERE tt.`TICKET_ID`=:ticketId";

        map.put("lastModifiedBy", curUser.getUserId());
        map.put("notes", newNotes);
        map.put("ticketId", ticketId);
        map.put("ticketTypeId", ticketTypeId);
        map.put("priorityId", t.getPriority().getPriorityId());

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, map, GlobalConstants.TAG_SYSTEMLOG));
        nm.update(sql, map);
    }

    @Override
    public Ticket getTicketByTicketId(int ticketId) {
        String sql = "SELECT tt.*,tm.`MARKETPLACE_NAME`,ttt.`TICKET_TYPE_DESC`,tp.`PRIORITY_NAME`,\n"
                + " tc.`COMPANY_NAME`,asn.`USERNAME`,tts.`STATUS_DESC`,crBy.`USERNAME` CREATED_BY_NAME, crBy.`USER_ID` FROM tkt_ticket tt \n"
                + " LEFT JOIN pm_marketplace tm ON tm.`MARKETPLACE_ID`=tt.`MARKETPLACE_ID`\n"
                + " LEFT JOIN tkt_ticket_type ttt ON ttt.`TICKET_TYPE_ID`=tt.`TICKET_TYPE_ID`\n"
                + " LEFT JOIN tkt_priority tp ON tp.`PRIORITY_ID`=tt.`TICKET_PRIORITY_ID`\n"
                + " LEFT JOIN tkt_company tc ON tc.`COMPANY_ID`=tt.`COMPANY_ID`\n"
                + " LEFT JOIN `user` asn ON asn.`USER_ID`=tt.`ASSIGNED_TO`\n"
                + "LEFT JOIN `user` crBy ON crBy.`USER_ID`=tt.`CREATED_BY`\n"
                + " LEFT JOIN tkt_ticket_status tts ON tts.`STATUS_ID`=tt.`TICKET_STATUS_ID`\n"
                + " WHERE tt.`TICKET_ID`=?";

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), GlobalConstants.TAG_SYSTEMLOG));
        Ticket ticket = this.jdbcTemplate.queryForObject(sql, new TicketRowMapper(), ticketId);
        String createdDateStr = DateUtils.convertDateToString(ticket.getCreateDate(), "yyyy/MM/dd HH:mm:ss");
        ticket.setCreatedDateStr(createdDateStr);

        return ticket;
    }

    @Override
    public List<TicketTrans> getTicketHistory(int ticketId) {
        String sql = "SELECT ttt.`TICKET_ID`,ttt.`TRANS_DATE`,ttt.`TRACKING_ID`,ttt.`TRACKING_CARRIER_NAME`,"
                + " asn.`USERNAME`,tts.`STATUS_DESC`,ttt.`TICKET_STATUS_ID`,ttt.`NOTES`,ttt.`TICKET_TYPE_ID`, "
                + " tp.`PRIORITY_NAME`,ty.`TICKET_TYPE_DESC`, usr.`USERNAME` as LAST_MODIFIED_BY FROM tkt_ticket_trans ttt \n"
                + " LEFT JOIN `user` asn ON asn.`USER_ID`=ttt.`ASSIGNED_TO`\n"
                + " LEFT JOIN `user` usr ON usr.`USER_ID`=ttt.`LAST_MODIFIED_BY`\n"
                + " LEFT JOIN tkt_ticket_status tts ON tts.`STATUS_ID`=ttt.`TICKET_STATUS_ID`\n"
                + " LEFT JOIN tkt_ticket_type ty ON ty.`TICKET_TYPE_ID`=ttt.`TICKET_TYPE_ID`"
                + " LEFT JOIN tkt_priority tp ON tp.`PRIORITY_ID`=ttt.`PRIORITY_ID`"
                + " WHERE ttt.`TICKET_ID`=? ORDER BY ttt.TRANS_DATE DESC";

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new TicketTransRowMapper(), ticketId);
    }

    @Override
    @Transactional
    public Ticket updateTicket(Ticket ticket) {
        CustomUserDetails curUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
        Map<String, Object> map = new HashMap<String, Object>();
        String sql = " UPDATE tkt_ticket tt SET tt.`TICKET_STATUS_ID`=:ticketStatusId ,";
//        if ((ticket.getTicketStatus().getStatusId() == 2) || (ticket.getTicketStatus().getStatusId() == 4 && ticket.getAssignedTo() != null)) {
//            sql += " tt.`ASSIGNED_ON`=COALESCE(tt.`ASSIGNED_ON`,:assignedOn),tt.`ASSIGNED_TO`=COALESCE(tt.`ASSIGNED_TO`,:assignedTo),";
//            map.put("assignedTo", ticket.getAssignedTo().getUserId());
//            map.put("assignedOn", curDate);
//        }
        if (ticket.getAssignedTo() != null) {
            sql += " tt.`ASSIGNED_ON`=:assignedOn,tt.`ASSIGNED_TO`=:assignedTo,";
            map.put("assignedTo", ticket.getAssignedTo().getUserId());
            map.put("assignedOn", curDate);
        }
        if (ticket.getTicketType().getTicketTypeId() == 3) {
            sql += " tt.`TRACKING_ID`=:trackingId,tt.`TRACKING_CARRIER_NAME`=:trackingCarrierName,";
            map.put("trackingId", ticket.getTrackingId());
            map.put("trackingCarrierName", ticket.getTrackingCarrierName());
        }

        sql += "  tt.`COMPLETED_ON`=:completedOn, tt.`LAST_MODIFIED_BY`=:lastModifiedBy, tt.`LAST_MODIFIED_DATE`=:lastModifiedDate, tt.NOTES=:notes "
                + " WHERE tt.`TICKET_ID`=:ticketId";

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(dataSource);
        map.put("ticketStatusId", ticket.getTicketStatus().getStatusId());
        if (ticket.getTicketStatus().getStatusId() == 5) {
            map.put("completedOn", curDate);
        } else {
            map.put("completedOn", ticket.getCompletedOn());
        }
        map.put("lastModifiedBy", curUser.getUserId());
        map.put("lastModifiedDate", curDate);
        map.put("notes", ticket.getNotes());
        map.put("ticketId", ticket.getTicketId());

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, map, GlobalConstants.TAG_SYSTEMLOG));
        int id = nm.update(sql, map);

        //Insert into ticket_trans
        sql = "INSERT INTO tkt_ticket_trans\n"
                + " SELECT NULL,NOW(),tt.`TICKET_ID`,";
        if ((ticket.getTicketStatus().getStatusId() == 2) || (ticket.getTicketStatus().getStatusId() == 4 && ticket.getAssignedTo() != null)) {
            sql += "COALESCE(tt.`ASSIGNED_TO`,:assignedTo),COALESCE(tt.`ASSIGNED_ON`,:assignedOn),";
            map.put("assignedTo", ticket.getAssignedTo().getUserId());
            map.put("assignedOn", curDate);
        } else {
            sql += "tt.`ASSIGNED_TO`,tt.`ASSIGNED_ON`,";
        }
        sql += ":ticketStatusId,:completedOn,";
        if (ticket.getTicketType().getTicketTypeId() == 3) {
            sql += " :trackingId,:trackingCarrierName,";
            map.put("trackingId", ticket.getTrackingId());
            map.put("trackingCarrierName", ticket.getTrackingCarrierName());
        } else {
            sql += " NULL,NULL,";
        }
        sql += "tt.`TICKET_TYPE_ID`,tt.`TICKET_PRIORITY_ID`,tt.`LEVEL`,:notes,:lastModifiedBy,NOW() FROM tkt_ticket tt\n"
                + " WHERE tt.`TICKET_ID`=:ticketId";

        if (ticket.getTicketStatus().getStatusId() == 5) {
            map.put("completedOn", curDate);
        } else {
            map.put("completedOn", ticket.getCompletedOn());
        }
        map.put("lastModifiedBy", curUser.getUserId());
        map.put("notes", ticket.getNotes());
        map.put("ticketId", ticket.getTicketId());

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, map, GlobalConstants.TAG_SYSTEMLOG));
        nm.update(sql, map);
        return ticket;
    }

    @Override
    public List<String> searchOrderId(String term) {
        String sql = "SELECT DISTINCT(t.`ORDER_ID`) FROM tkt_ticket  t WHERE t.`ORDER_ID` LIKE '%" + term + "%'";

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        return list;
    }

    @Override
    public List<Ticket> getTicketListByOrderId(String orderId) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT tt.`TICKET_ID`,tt.`TICKET_NO`,tm.`MARKETPLACE_NAME`,tt.`TICKET_TYPE_ID`,ttt.`TICKET_TYPE_DESC`,\n"
                + "tp.`PRIORITY_NAME`,tt.`ORDER_ID`,COALESCE(tt.`TRACKING_ID`,'') TRACKING_ID,tt.`TRACKING_CARRIER_NAME`,\n"
                + "tt.`DESCRIPTION`,tc.`COMPANY_NAME`,tt.`CUST_NAME`,tt.`CUST_PHONE_NO`,tt.`CUST_EMAIL_ID`,tt.`ASSIGNED_TO`,\n"
                + "COALESCE(asn.`USERNAME`,'') USERNAME,tt.`ASSIGNED_ON`,tt.`TICKET_STATUS_ID`,tts.`STATUS_DESC`,\n"
                + "tt.`COMPLETED_ON`,tt.`LAST_MODIFIED_BY`,tt.`CREATED_BY`,crBy.`USERNAME` CREATED_BY_NAME,tt.`CREATED_DATE`, tt.`DETAILS` \n"
                + "FROM tkt_ticket tt \n"
                + "LEFT JOIN pm_marketplace tm ON tm.`MARKETPLACE_ID`=tt.`MARKETPLACE_ID`\n"
                + "LEFT JOIN tkt_ticket_type ttt ON ttt.`TICKET_TYPE_ID`=tt.`TICKET_TYPE_ID`\n"
                + "LEFT JOIN tkt_priority tp ON tp.`PRIORITY_ID`=tt.`TICKET_PRIORITY_ID`\n"
                + "LEFT JOIN tkt_company tc ON tc.`COMPANY_ID`=tt.`COMPANY_ID`\n"
                + "LEFT JOIN `user` asn ON asn.`USER_ID`=tt.`ASSIGNED_TO`\n"
                + "LEFT JOIN `user` crBy ON crBy.`USER_ID`=tt.`CREATED_BY`\n"
                + "LEFT JOIN tkt_ticket_status tts ON tts.`STATUS_ID`=tt.`TICKET_STATUS_ID`\n"
                + "WHERE tt.`ORDER_ID`=:orderId");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderId", orderId);

        sql.append(" ORDER BY tt.`LAST_MODIFIED_DATE` DESC");

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), params, GlobalConstants.TAG_SYSTEMLOG));
        return nm.query(sql.toString(), params, new TicketRowMapper());
    }

    @Override
    public List<Ticket> getTicketListByPhoneNumber(String phoneNumber) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT tt.`TICKET_ID`,tt.`TICKET_NO`,tm.`MARKETPLACE_NAME`,tt.`TICKET_TYPE_ID`,ttt.`TICKET_TYPE_DESC`,\n"
                + "tp.`PRIORITY_NAME`,tt.`ORDER_ID`,COALESCE(tt.`TRACKING_ID`,'') TRACKING_ID,tt.`TRACKING_CARRIER_NAME`,\n"
                + "tt.`DESCRIPTION`,tc.`COMPANY_NAME`,tt.`CUST_NAME`,tt.`CUST_PHONE_NO`,tt.`CUST_EMAIL_ID`,tt.`ASSIGNED_TO`,\n"
                + "COALESCE(asn.`USERNAME`,'') USERNAME,tt.`ASSIGNED_ON`,tt.`TICKET_STATUS_ID`,tts.`STATUS_DESC`,\n"
                + "tt.`COMPLETED_ON`,tt.`LAST_MODIFIED_BY`,tt.`CREATED_BY`,crBy.`USERNAME` CREATED_BY_NAME,tt.`CREATED_DATE`, tt.`DETAILS` \n"
                + "FROM tkt_ticket tt \n"
                + "LEFT JOIN pm_marketplace tm ON tm.`MARKETPLACE_ID`=tt.`MARKETPLACE_ID`\n"
                + "LEFT JOIN tkt_ticket_type ttt ON ttt.`TICKET_TYPE_ID`=tt.`TICKET_TYPE_ID`\n"
                + "LEFT JOIN tkt_priority tp ON tp.`PRIORITY_ID`=tt.`TICKET_PRIORITY_ID`\n"
                + "LEFT JOIN tkt_company tc ON tc.`COMPANY_ID`=tt.`COMPANY_ID`\n"
                + "LEFT JOIN `user` asn ON asn.`USER_ID`=tt.`ASSIGNED_TO`\n"
                + "LEFT JOIN `user` crBy ON crBy.`USER_ID`=tt.`CREATED_BY`\n"
                + "LEFT JOIN tkt_ticket_status tts ON tts.`STATUS_ID`=tt.`TICKET_STATUS_ID`\n"
                + "WHERE tt.`CUST_PHONE_NO`=:custPhoneNumber");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("custPhoneNumber", phoneNumber);

        sql.append(" ORDER BY tt.`LAST_MODIFIED_DATE` DESC");

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), params, GlobalConstants.TAG_SYSTEMLOG));
        return nm.query(sql.toString(), params, new TicketRowMapper());
    }

    @Override
    public List<User> getAssignedToSupervisorList(boolean active) {
        String sql = "SELECT u.`USER_ID`,u.`USERNAME` FROM `user` u\n"
                + "LEFT JOIN `user_role`ur  ON ur.`USER_ID`=u.`USER_ID`\n"
                + "WHERE ur.`ROLE_ID` IN ('ROLE_MANAGER','ROLE_SUPERVISOR')";

        if (active) {
            sql += " AND u.`ACTIVE`";
        }
        sql += "ORDER BY u.`USERNAME`";
        return this.jdbcTemplate.query(sql, new AssignedToRowMapper());
    }

    @Override
    public Ticket getTicketByPhoneNumber(String phoneNumber) {
        try {
            String sql = "SELECT tt.`TICKET_ID`,tt.`TICKET_NO`,tm.`MARKETPLACE_NAME`,tt.`TICKET_TYPE_ID`,ttt.`TICKET_TYPE_DESC`,\n"
                    + "tp.`PRIORITY_NAME`,tt.`ORDER_ID`,COALESCE(tt.`TRACKING_ID`,'') TRACKING_ID,tt.`TRACKING_CARRIER_NAME`,\n"
                    + "tt.`DESCRIPTION`,tc.`COMPANY_NAME`,tt.`CUST_NAME`,tt.`CUST_PHONE_NO`,tt.`CUST_EMAIL_ID`,tt.`ASSIGNED_TO`,\n"
                    + "COALESCE(asn.`USERNAME`,'') USERNAME,tt.`ASSIGNED_ON`,tt.`TICKET_STATUS_ID`,tts.`STATUS_DESC`,\n"
                    + "tt.`COMPLETED_ON`,tt.`LAST_MODIFIED_BY`,tt.`CREATED_BY`,crBy.`USERNAME` CREATED_BY_NAME,tt.`CREATED_DATE`, tt.`DETAILS` \n"
                    + "FROM tkt_ticket tt \n"
                    + "LEFT JOIN pm_marketplace tm ON tm.`MARKETPLACE_ID`=tt.`MARKETPLACE_ID`\n"
                    + "LEFT JOIN tkt_ticket_type ttt ON ttt.`TICKET_TYPE_ID`=tt.`TICKET_TYPE_ID`\n"
                    + "LEFT JOIN tkt_priority tp ON tp.`PRIORITY_ID`=tt.`TICKET_PRIORITY_ID`\n"
                    + "LEFT JOIN tkt_company tc ON tc.`COMPANY_ID`=tt.`COMPANY_ID`\n"
                    + "LEFT JOIN `user` asn ON asn.`USER_ID`=tt.`ASSIGNED_TO`\n"
                    + "LEFT JOIN `user` crBy ON crBy.`USER_ID`=tt.`CREATED_BY`\n"
                    + "LEFT JOIN tkt_ticket_status tts ON tts.`STATUS_ID`=tt.`TICKET_STATUS_ID`\n"
                    + "WHERE tt.`CUST_PHONE_NO`=?";


            NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
            //LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), params, GlobalConstants.TAG_SYSTEMLOG));
            return this.jdbcTemplate.queryForObject(sql, new TicketRowMapper(), phoneNumber);
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public Order getOrderByPhoneNumber(String phoneNumber) {
        String sql = "SELECT tor.* FROM pm_order tor WHERE tor.CUSTOMER_PHONE_NO=?";

        return this.jdbcTemplate.queryForObject(sql, new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet rs, int i) throws SQLException {
                Order order = new Order();
                Marketplace mp = new Marketplace();
                mp.setMarketplaceId(rs.getInt("MARKETPLACE_ID"));
                order.setMarketplace(mp);
                order.setMarketplaceOrderId(rs.getString("MARKETPLACE_ORDER_ID"));
                order.setCustomerName(rs.getString("CUSTOMER_NAME"));
                order.setCustomerPhoneNo(rs.getString("CUSTOMER_PHONE_NO"));
                return order;
            }
        }, phoneNumber);

    }
}
