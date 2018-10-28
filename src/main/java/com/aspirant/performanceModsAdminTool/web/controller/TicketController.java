/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.exception.CouldNotBuildExcelException;
import com.aspirant.performanceModsAdminTool.framework.ApplicationSession;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.Company;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.DTO.TicketFilterDTO;
import com.aspirant.performanceModsAdminTool.model.Marketplace;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.Ticket;
import com.aspirant.performanceModsAdminTool.model.TicketPriority;
import com.aspirant.performanceModsAdminTool.model.TicketStatus;
import com.aspirant.performanceModsAdminTool.model.TicketTrans;
import com.aspirant.performanceModsAdminTool.model.TicketType;
import com.aspirant.performanceModsAdminTool.model.User;
import com.aspirant.performanceModsAdminTool.service.TicketService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import com.aspirant.utils.POI.POICell;
import com.aspirant.utils.POI.POIRow;
import com.aspirant.utils.POI.POIWorkSheet;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Ritesh
 */
@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateTimeFormat, true));
    }

    @RequestMapping(value = "/tickets/createTicket.htm", method = RequestMethod.GET)
    public String showAddTicket(ModelMap map) {
        Ticket ticket = new Ticket();
        map.addAttribute("ticket", ticket);

        List<Marketplace> marketplaceList = ApplicationSession.getCurrent().getMarketplaceListActive();
        map.addAttribute("marketplaceList", marketplaceList);

        List<TicketType> ticketTypeList = ApplicationSession.getCurrent().getTicketTypeListActive();
        map.addAttribute("ticketTypeList", ticketTypeList);

        List<Company> companyList = ApplicationSession.getCurrent().getCompanyListActive();
        map.addAttribute("companyList", companyList);

        //0-new,assigned and complated : status required while ticket creation
        List<TicketStatus> ticketStatusList = this.ticketService.getTicketStatusList(0);
        map.addAttribute("ticketStatusList", ticketStatusList);

//        List<User> assignedToList = this.ticketService.getAssignedToList(true);
//        map.addAttribute("assignedToList", assignedToList);

        return "/tickets/createTicket";
    }

    @RequestMapping(value = "/tickets/createTicket.htm", method = RequestMethod.POST)
    public String showCreateTicket(@ModelAttribute("ticket") Ticket ticket, Errors errors, ModelMap map, HttpServletRequest request) throws IOException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:../home/home.htm?msg=msg.actionCancelled";
        } else {
            Ticket createdTicket = this.ticketService.createNewTicket(ticket);
            if (createdTicket == null) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../tickets/createTicket.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                String msg = "Ticket created successfully.Ticket No. : " + createdTicket.getTicketNo();
                return "redirect:../tickets/listOpenTickets.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }

    @RequestMapping(value = "/tickets/createTicketForDialer.htm", method = RequestMethod.GET)
    public String showAddTicketForDialer(@RequestParam(value = "phoneNumber", required = true) String phoneNumber, ModelMap map) {


        Ticket ticket = this.ticketService.getTicketByPhoneNumber(phoneNumber);
        if (ticket == null) {
            ticket = new Ticket();
            Order order = this.ticketService.getOrderByPhoneNumber(phoneNumber);
            ticket.setMarketplace(order.getMarketplace());
            ticket.setCustomerName(order.getCustomerName());
            ticket.setCustPhoneNumber(order.getCustomerPhoneNo());
            ticket.setOrderId(order.getMarketplaceOrderId());
        }
        map.addAttribute("ticket", ticket);

        List<Ticket> ticketList = this.ticketService.getTicketListByPhoneNumber(phoneNumber);
        map.addAttribute("ticketList", ticketList);

        List<Marketplace> marketplaceList = ApplicationSession.getCurrent().getMarketplaceListActive();
        map.addAttribute("marketplaceList", marketplaceList);

        List<TicketType> ticketTypeList = ApplicationSession.getCurrent().getTicketTypeListActive();
        map.addAttribute("ticketTypeList", ticketTypeList);

        List<Company> companyList = ApplicationSession.getCurrent().getCompanyListActive();
        map.addAttribute("companyList", companyList);

        //0-new,assigned and complated : status required while ticket creation
        List<TicketStatus> ticketStatusList = this.ticketService.getTicketStatusList(0);
        map.addAttribute("ticketStatusList", ticketStatusList);

        return "/tickets/createTicketForDialer";
    }

    @RequestMapping(value = "/tickets/createTicketForDialer.htm", method = RequestMethod.POST)
    public String showAddTicketForDialer(@ModelAttribute("ticket") Ticket ticket, Errors errors, ModelMap map, HttpServletRequest request) throws IOException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            return "redirect:../home/home.htm?msg=msg.actionCancelled";
        } else {
            Ticket createdTicket = this.ticketService.createNewTicket(ticket);
            if (createdTicket == null) {
                String error = "Sorry! Your data not saved.";
                return "redirect:../tickets/createTicket.htm?error=" + URLEncoder.encode(error, "UTF-8");
            } else {
                String msg = "Ticket created successfully.Ticket No. : " + createdTicket.getTicketNo();
                return "redirect:../tickets/listOpenTickets.htm?msg=" + URLEncoder.encode(msg, "UTF-8");
            }
        }
    }

    @RequestMapping(value = "/tickets/listAllTickets.htm")
    public String listAllTickets(@ModelAttribute("ticketFilterDTO") TicketFilterDTO ticketFilterDTO, HttpServletRequest request, ModelMap model) {
        ticketFilterDTO.setStatusMatrixId(-1);//-1 for all tickets
        ticketFilterDTO.setRead(0);
        int viewType = -1;
        model.addAttribute("ticketFilterDTO", ticketFilterDTO);

        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_MONTH, 1);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String startDate = ServletRequestUtils.getStringParameter(request, "startDate", df.format(date.getTime()));
        String stopDate = ServletRequestUtils.getStringParameter(request, "stopDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));

        List<Marketplace> marketplaceList = ApplicationSession.getCurrent().getMarketplaceListActive();
        model.addAttribute("marketplaceList", marketplaceList);

        List<TicketType> ticketTypeList = ApplicationSession.getCurrent().getTicketTypeListActive();
        model.addAttribute("ticketTypeList", ticketTypeList);

        List<Company> companyList = ApplicationSession.getCurrent().getCompanyListActive();
        model.addAttribute("companyList", companyList);

        List<User> assignedToList = this.ticketService.getAssignedToList(false);
        model.addAttribute("assignedToList", assignedToList);

        List<TicketStatus> ticketStatusList = this.ticketService.getTicketStatusList(-1);
        model.addAttribute("ticketStatusList", ticketStatusList);

        model.addAttribute("startDate", startDate);
        model.addAttribute("stopDate", stopDate);

        List<Ticket> ticketList = this.ticketService.getAllTicketList(startDate, stopDate, ticketFilterDTO, viewType);
        model.addAttribute("ticketList", ticketList);
        return "/tickets/listAllTickets";
    }

    @RequestMapping(value = "/report/allTicketListExcel.htm")
    public void getAllTicketListExcel(@ModelAttribute("ticketFilterDTO") TicketFilterDTO ticketFilterDTO, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws CouldNotBuildExcelException {
        try {
            Calendar date = Calendar.getInstance();
            date.set(Calendar.DAY_OF_MONTH, 1);
            int viewType = 1;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            CustomUserDetails curUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String startDate = ServletRequestUtils.getStringParameter(request, "startDate", df.format(date.getTime()));
            String stopDate = ServletRequestUtils.getStringParameter(request, "stopDate", DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD));
            ticketFilterDTO.setStatusMatrixId(-1);//-1 for all tickets
            ticketFilterDTO.setRead(0);

            List<Ticket> ticketList = this.ticketService.getAllTicketList(startDate, stopDate, ticketFilterDTO, viewType);

            OutputStream out = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment;filename=AllTicketList-" + startDate + "_to_" + stopDate + ".xls");
            response.setContentType("application/vnd.ms-excel");
            POIWorkSheet mySheet = new POIWorkSheet(out, "AllTicketList");
            mySheet.setPrintTitle(false);
            POIRow headerRow = new POIRow(POIRow.HEADER_ROW);
            headerRow.addCell("Ticket Number");
            headerRow.addCell("Created By");
            headerRow.addCell("Marketplace Name");
            headerRow.addCell("Ticket Type");
            headerRow.addCell("Priority");
            headerRow.addCell("Order Id");
            headerRow.addCell("Tracking Id");
            headerRow.addCell("Tracking Carrier Name");
            headerRow.addCell("Description");
            headerRow.addCell("Company Name");
            headerRow.addCell("Customer Name");
            headerRow.addCell("Customer Phone No.");
            headerRow.addCell("Customer Email Id");
            headerRow.addCell("Assigned To");
            headerRow.addCell("Assigned On");
            headerRow.addCell("Ticket Status");
            headerRow.addCell("Completed On");

            mySheet.addRow(headerRow);

            for (Ticket data : ticketList) {
                POIRow dataRow = new POIRow();
                dataRow.addCell(data.getTicketNo(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getCreatedBy().getUsername(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getMarketplace().getMarketplaceName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getTicketType().getTicketTypeDesc(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getTicketPriority().getPriorityName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getOrderId(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getTrackingId(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getTrackingCarrierName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getDescription(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getCompany().getCompanyName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getCustomerName(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getCustPhoneNumber(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getCustEmailId(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getAssignedTo().getUsername(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getAssignedOn(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getTicketStatus().getStatusDesc(), POICell.TYPE_TEXT);
                dataRow.addCell(data.getCompletedOn(), POICell.TYPE_TEXT);
                mySheet.addRow(dataRow);
            }
            mySheet.writeWorkBook();
            out.close();
            out.flush();
        } catch (IOException io) {
            LogUtils.systemLogger.warn(LogUtils.buildStringForLog(io, GlobalConstants.TAG_SYSTEMLOG));
            throw new CouldNotBuildExcelException(io.getMessage());
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            throw new CouldNotBuildExcelException(e.getMessage());
        }
    }

    @RequestMapping(value = "/tickets/listOpenTickets.htm")
    public String listOpenTickets(@ModelAttribute("ticketFilterDTO") TicketFilterDTO ticketFilterDTO, HttpServletRequest request, ModelMap model) {
        CustomUserDetails curUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ticketFilterDTO.setStatusMatrixId(-2);//-2 for open tickets
        model.addAttribute("ticketFilterDTO", ticketFilterDTO);

        List<Marketplace> marketplaceList = ApplicationSession.getCurrent().getMarketplaceListActive();
        model.addAttribute("marketplaceList", marketplaceList);

        List<TicketType> ticketTypeList = ApplicationSession.getCurrent().getTicketTypeListActive();
        model.addAttribute("ticketTypeList", ticketTypeList);

        List<Company> companyList = ApplicationSession.getCurrent().getCompanyListActive();
        model.addAttribute("companyList", companyList);

        List<User> assignedToList = this.ticketService.getAssignedToList(false);
        model.addAttribute("assignedToList", assignedToList);

        List<TicketStatus> ticketStatusList = this.ticketService.getTicketStatusList(-2);
        model.addAttribute("ticketStatusList", ticketStatusList);

        int viewType = 1;
        if (curUser.getBusinessFunction().contains(new SimpleGrantedAuthority("ROLE_BF_VIEW_ALL_OPEN_TICKETS"))) {
            viewType = -1;
        } else if (curUser.getBusinessFunction().contains(new SimpleGrantedAuthority("ROLE_BF_VIEW_OWN_OPEN_TICKETS"))) {
            viewType = 1;
        }
        List<Ticket> ticketList = this.ticketService.getAllTicketList(null, null, ticketFilterDTO, viewType);
        model.addAttribute("ticketList", ticketList);
        return "/tickets/listOpenTickets";
    }

    @RequestMapping(value = "/tickets/editTicket.htm", method = RequestMethod.GET)
    public String showEditTask(@RequestParam(value = "ticketId") int ticketId, @RequestParam(value = "uniqueCode", required = true) int uniqueCode, ModelMap map) {

        map.addAttribute("uniqueCode", uniqueCode);
        Ticket ticket = this.ticketService.getTicketByTicketId(ticketId);
        map.addAttribute("ticket", ticket);
        List<TicketTrans> ticketHistoryList = this.ticketService.getTicketHistory(ticketId);
        map.addAttribute("ticketHistoryList", ticketHistoryList);
        //0-new,assigned and complated : status required while ticket creation
        List<TicketStatus> ticketStatusList = this.ticketService.getTicketStatusList(ticket.getTicketStatus().getStatusId());
        map.addAttribute("ticketStatusList", ticketStatusList);
        int ticketTypeId = ticket.getTicketType().getTicketTypeId();
        if (ticketTypeId == 5) {
            List<User> assignedToList = this.ticketService.getAssignedToSupervisorList(true);
            map.addAttribute("assignedToList", assignedToList);
        } else {
            List<User> assignedToList = this.ticketService.getAssignedToList(true);
            map.addAttribute("assignedToList", assignedToList);
        }


        return "/tickets/editTicket";
    }

    @RequestMapping(value = "/tickets/editTicket.htm", method = RequestMethod.POST)
    public String editTask(@RequestParam(value = "uniqueCode", required = true) int uniqueCode, @ModelAttribute("ticket") Ticket ticket, Errors errors, ModelMap map, HttpServletRequest request) throws UnsupportedEncodingException {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            if (uniqueCode == 1) {
                return "redirect:../tickets/listAllTickets.htm?msg=msg.actionCancelled";
            } else if (uniqueCode == 2) {
                return "redirect:../tickets/listOpenTickets.htm?msg=msg.actionCancelled";
            } else {
                return "redirect:../tickets/createTicket.htm?msg=msg.actionCancelled";
            }
        } else {
            String notes = ServletRequestUtils.getStringParameter(request, "notes", "");
            ticket.setNotes(notes);
            int result = this.ticketService.updateTicket(ticket);
            if (result == 1) {
                LogUtils.systemLogger.info(LogUtils.buildStringForLog("Ticket updated successfully for ticket Id : " + ticket.getTicketId(), GlobalConstants.TAG_SYSTEMLOG));
                String message = "Ticket updated Successfully for Ticket No :" + ticket.getTicketNo();
                if (uniqueCode == 1) {
                    return "redirect:../tickets/listAllTickets.htm?msg=" + URLEncoder.encode(message, "UTF-8");
                } else if (uniqueCode == 2) {
                    return "redirect:../tickets/listOpenTickets.htm?msg=" + URLEncoder.encode(message, "UTF-8");
                } else {
                    return "redirect:../tickets/createTicket.htm?msg=" + URLEncoder.encode(message, "UTF-8");
                }
            } else {
                LogUtils.systemLogger.error(LogUtils.buildStringForLog("Error occured while updating task for id : " + ticket.getTicketId(), GlobalConstants.TAG_SYSTEMLOG));
                String error = "Sorry! Your data not saved";
                if (uniqueCode == 1) {
                    return "redirect:../tickets/listAllTickets.htm?error=" + URLEncoder.encode(error, "UTF-8");
                } else {
                    return "redirect:../tickets/listOpenTickets.htm?error=" + URLEncoder.encode(error, "UTF-8");
                }
            }
        }
    }
}
