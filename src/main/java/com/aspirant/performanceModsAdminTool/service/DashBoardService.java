/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

import com.aspirant.performanceModsAdminTool.model.Ticket;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ritesh
 */
public interface DashBoardService {
    
    //public Ticket searchTicketByTicketNo(String ticketNo);

    /**
     * this method gives details of tickets those are open
     * @return 
     */
    public Map<String, Object> getOpenTicketDetails();
    
    
    /**
     * this method gives details of ticket on monthly basis
     * @return 
     */
    public Map<String, Object> getMonthlyTicketDetails();
    
    /**
     * 
     * @return 
     */
    public Map<String, Object> getMonthlyTatDetails();
    
    /**
     * 
     * @return 
     */
    public Map<String, Object> getTicketTypeTat();
    
    /**
     * 
     * @return 
     */
    public Map<String, Object> getPercentageFcr();
    /**
     * 
     * @return 
     */
    public Map<String, Object> getSummaryForGraph();
    
    /**
     * this method will return List of unshipped order data of last 7 days
     * @return 
     */
    public List<Map<String, Object>> getLast7daysUnshippedOrderData();
    
    public Map<String, Object> getOrderShippmentDetails();
    
    public Map<String, Object> getProductDetailsForWebsite();

    public Map<String, Object> getPricesDifferenceAndLowCountProduct();
}
