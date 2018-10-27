/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao;

import com.aspirant.performanceModsAdminTool.model.DTO.TicketFilterDTO;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.Ticket;
import com.aspirant.performanceModsAdminTool.model.TicketPriority;
import com.aspirant.performanceModsAdminTool.model.TicketStatus;
import com.aspirant.performanceModsAdminTool.model.TicketTrans;
import com.aspirant.performanceModsAdminTool.model.TicketType;
import com.aspirant.performanceModsAdminTool.model.User;
import java.util.List;

/**
 *
 * @author Ritesh
 */
public interface TicketDao {

    /**
     * method is used to get ticket type list
     *
     * @return list of ticket types
     */
    public List<TicketType> getTicketTypeList();

    /**
     * method is used to get ticket status list based on given status
     *
     * @param currentStatusId status based on which we will get status list as
     * mapped in ticket matrix
     * @return list of ticket Statuses
     */
    public List<TicketStatus> getTicketStatusList(int currentStatusId);

    /**
     * Method used to get assigned to user list
     *
     * @param active show only active list if its true
     * @return list of assigned to users
     */
    public List<User> getAssignedToList(boolean active);
    
    /**
     * Method used to get assigned to user list ha
     *
     * @param active show only active list if its true
     * @return list of assigned to users
     */
    public List<User> getAssignedToSupervisorList(boolean active);

    /**
     * method is used to create new ticket
     *
     * @param ticket ticket object with ticket information
     * @return updated ticket object
     */
    public Ticket createNewTicket(Ticket ticket);

    /**
     * method is used to search customer name for autocomplete
     *
     * @param term string to search customer name
     * @return list of matched customer names
     */
    public List<String> searchCustomerName(String term);

    /**
     * method is used to search ticket number for autocomplete
     *
     * @param term string to search ticket number
     * @return list of matched ticket numbers
     */
    public List<String> searchTicketNo(String term);

    /**
     * method is used to get list of all tickets based on filters
     *
     * @param startDate statrtDate is a date from when you want to get the list
     * of tickets
     * @param stopDate stopDate is a date till when you want to get the list of
     * tickets
     * @param ticketFilterDTO ticketFilterDTO object contains filters to get
     * ticket list
     *
     * @return list of tickets
     */
    public List<Ticket> getAllTicketList(String startDate, String stopDate, TicketFilterDTO ticketFilterDTO, int viewType);

    /**
     * method is used to reopen ticket. It updates ticket status to new from
     * completed or canceled status.
     *
     * @param ticketId ticket id of ticket to reopen
     * @param ticketTypeId changes ticket type id while reopen
     * @param notes reason for reopen
     */
    public void reopenTicket(int ticketId, int ticketTypeId, String notes);

    /**
     * method is used to get ticket object by ticket id
     *
     * @param ticketId ticket id to get object
     * @return ticket object
     */
    public Ticket getTicketByTicketId(int ticketId);

    /**
     * method is used to get ticket history by ticket id
     *
     * @param ticketId ticket id to get ticket history
     * @return list of ticket history
     */
    public List<TicketTrans> getTicketHistory(int ticketId);

    /**
     * method used to update ticket information
     *
     * @param ticket Ticket object with updated information
     */
    public Ticket updateTicket(Ticket ticket);

    /**
     * method is used to search order id for autocomplete
     *
     * @param term string to search order id
     * @return list of matched order ids
     */
    public List<String> searchOrderId(String term);

    /**
     * method is used to get list of all tickets based on filters
     *
     * @param orderId order id based on which we need to get list of tickets
     *
     * @return list of tickets
     */
    public List<Ticket> getTicketListByOrderId(String orderId);
    
    /**
     * method is used to get list of all tickets based on customer phone number
     *
     * @param phoneNumber phone number based on which we need to get list of tickets
     *
     * @return list of tickets
     */
    public List<Ticket> getTicketListByPhoneNumber(String phoneNumber);
    
    public Ticket getTicketByPhoneNumber(String phoneNumber);
    
    public Order getOrderByPhoneNumber(String phoneNumber);
}
