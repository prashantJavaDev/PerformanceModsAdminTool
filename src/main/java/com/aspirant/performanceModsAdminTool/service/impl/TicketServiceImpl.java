/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.EmailerDao;
import com.aspirant.performanceModsAdminTool.dao.TicketDao;
import com.aspirant.performanceModsAdminTool.dao.UserDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.DTO.TicketFilterDTO;
import com.aspirant.performanceModsAdminTool.model.Email;
import com.aspirant.performanceModsAdminTool.model.EmailTemplate;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.Ticket;
import com.aspirant.performanceModsAdminTool.model.TicketStatus;
import com.aspirant.performanceModsAdminTool.model.TicketTrans;
import com.aspirant.performanceModsAdminTool.model.User;
import com.aspirant.performanceModsAdminTool.service.EmailerService;
import com.aspirant.performanceModsAdminTool.service.TicketService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ritesh
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private EmailerDao emailerDao;
    @Autowired
    private EmailerService emailerService;
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAssignedToList(boolean active) {
        return this.ticketDao.getAssignedToList(active);
    }

    @Override
    public List<TicketStatus> getTicketStatusList(int currentStatusId) {
        return this.ticketDao.getTicketStatusList(currentStatusId);
    }

    @Override
    public Ticket createNewTicket(Ticket ticket) {
        CustomUserDetails curUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Ticket tt = this.ticketDao.createNewTicket(ticket);
            User requestedBy = this.userDao.getUserByUserId(ticket.getAssignedTo().getUserId());
            String ticketNo = tt.getTicketNo();
            if (tt.getAssignedTo().getUserId() != 0) {
                EmailTemplate emailTemplate = this.emailerDao.getEmailTemplateByTemplateId(1);
                String[] subjectParam = new String[]{ticketNo};
                String[] bodyParam = new String[]{curUser.getUsername()};
                Email email = this.emailerDao.buildEmail(emailTemplate.getEmailTemplateId(), requestedBy.getEmailId(), subjectParam, bodyParam, emailTemplate);
                email.setToSend(requestedBy.getEmailId());
                email.setTicket(ticket);
                String ccList = "mohit.g@altiusbpo.in,paresh@altius.cc,rahul.d@altiusbpo.in,rohit@altius.cc";
//                String ccList = "ritesh.b@altius.cc";
                email.setCcToSend(ccList);
                int emailerId = this.emailerDao.addEmail(email);
                email.setEmailerId(emailerId);
                this.emailerService.sendEmail(email);
            }
            LogUtils.systemLogger.info(LogUtils.buildStringForLog("New ticket created successfully for id : " + tt.getTicketNo(), GlobalConstants.TAG_SYSTEMLOG));
            return tt;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.systemLogger.error(LogUtils.buildStringForLog("Error occured while adding new ticket : " + e, GlobalConstants.TAG_SYSTEMLOG));
            return null;
        }
    }

    @Override
    public List<String> searchCustomerName(String term) {
        return this.ticketDao.searchCustomerName(term);
    }

    @Override
    public List<String> searchTicketNo(String term) {
        return this.ticketDao.searchTicketNo(term);
    }

    @Override
    public List<Ticket> getAllTicketList(String startDate, String stopDate, TicketFilterDTO ticketFilterDTO, int viewType) {
        CustomUserDetails curUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Ticket> ticketList = this.ticketDao.getAllTicketList(startDate, stopDate, ticketFilterDTO, viewType);
        for (Ticket t : ticketList) {
            if (t.getLastModifiedBy().getUserId() == curUser.getUserId() && t.getTicketStatus().getStatusId() != 1) {
                t.setRead(true);
            } else {
                t.setRead(false);
            }
        }
        return ticketList;
    }

    @Override
    public int reopenTicket(int ticketId, int ticketTypeId, String notes) {
        try {
            this.ticketDao.reopenTicket(ticketId, ticketTypeId, notes);
            LogUtils.systemLogger.info(LogUtils.buildStringForLog("Ticket reopened successfully.", GlobalConstants.TAG_SYSTEMLOG));
            return 1;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public Ticket getTicketByTicketId(int ticketId) {
        return this.ticketDao.getTicketByTicketId(ticketId);
    }

    @Override
    public List<TicketTrans> getTicketHistory(int ticketId) {
        return this.ticketDao.getTicketHistory(ticketId);
    }

    @Override
    public int updateTicket(Ticket ticket) {
        CustomUserDetails curUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Ticket tt = this.ticketDao.updateTicket(ticket);
            if (tt.getTicketStatus().getStatusId() == 4) {
                User createdBy = this.userDao.getUserByUserId(ticket.getCreatedBy().getUserId());
                User requestedBy = this.userDao.getUserByUserId(curUser.getUserId());
                String ticketNo = tt.getTicketNo();
                EmailTemplate emailTemplate = this.emailerDao.getEmailTemplateByTemplateId(2);
                String[] subjectParam = new String[]{ticketNo};
                String[] bodyParam = new String[]{curUser.getUsername()};
                Email email = this.emailerDao.buildEmail(emailTemplate.getEmailTemplateId(), requestedBy.getEmailId(), subjectParam, bodyParam, emailTemplate);
                email.setToSend(createdBy.getEmailId());
                email.setTicket(ticket);
                String ccList = "mohit.g@altiusbpo.in,paresh@altius.cc,rahul.d@altiusbpo.in,rohit@altius.cc";
//                String ccList = "ritesh.b@altius.cc";
                email.setCcToSend(ccList);
                int emailerId = this.emailerDao.addEmail(email);
                email.setEmailerId(emailerId);
                this.emailerService.sendEmail(email);
            }

            if (tt.getTicketStatus().getStatusId() == 2) {
                User createdBy = this.userDao.getUserByUserId(ticket.getCreatedBy().getUserId());
                User assignTo = this.userDao.getUserByUserId(ticket.getAssignedTo().getUserId());
                String ticketNo = tt.getTicketNo();
                EmailTemplate emailTemplate = this.emailerDao.getEmailTemplateByTemplateId(1);
                String[] subjectParam = new String[]{ticketNo};
                String[] bodyParam = new String[]{curUser.getUsername()};
                Email email = this.emailerDao.buildEmail(emailTemplate.getEmailTemplateId(), assignTo.getEmailId(), subjectParam, bodyParam, emailTemplate);
                email.setToSend(assignTo.getEmailId());
                email.setTicket(ticket);
                String ccList = "mohit.g@altiusbpo.in,paresh@altius.cc,rahul.d@altiusbpo.in,rohit@altius.cc";
                //String ccList = "ritesh.b@altius.cc";
                email.setCcToSend(ccList);
                int emailerId = this.emailerDao.addEmail(email);
                email.setEmailerId(emailerId);
                this.emailerService.sendEmail(email);
            }

            LogUtils.systemLogger.info(LogUtils.buildStringForLog("Ticket updated successfully.", GlobalConstants.TAG_SYSTEMLOG));
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public List<String> searchOrderId(String term) {
        return this.ticketDao.searchOrderId(term);
    }

    @Override
    public List<Ticket> getTicketListByOrderId(String orderId) {
        return this.ticketDao.getTicketListByOrderId(orderId);
    }

    @Override
    public List<User> getAssignedToSupervisorList(boolean active) {
        return this.ticketDao.getAssignedToSupervisorList(active);
    }

    @Override
    public List<Ticket> getTicketListByPhoneNumber(String phoneNumber) {
        return this.ticketDao.getTicketListByPhoneNumber(phoneNumber);
    }

    @Override
    public Ticket getTicketByPhoneNumber(String phoneNumber) {
        return this.ticketDao.getTicketByPhoneNumber(phoneNumber);
    }

    @Override
    public Order getOrderByPhoneNumber(String phoneNumber) {
        return this.ticketDao.getOrderByPhoneNumber(phoneNumber);
    }
}
