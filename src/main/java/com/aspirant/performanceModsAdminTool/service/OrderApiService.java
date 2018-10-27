/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

import com.amazonservices.mws.orders.model.ListOrdersResponse;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Ritesh
 */
public interface OrderApiService {
    
    /**
     * this method is used to get the order list
     * @param lastUpdatedAfterDate
     * @return ListOrdersResponse
     */
    public ListOrdersResponse getOrderList(XMLGregorianCalendar lastUpdatedAfterDate);
}
