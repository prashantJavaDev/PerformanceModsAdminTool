/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.service.OrderApiService;
import com.aspirant.performanceModsAdminTool.service.OrderService;
import com.amazonservices.mws.client.MwsUtl;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Ritesh
 */
@Controller
public class OrderController {

    @Autowired
    OrderApiService orderApiService;
    @Autowired
    OrderService orderService;

//    @Scheduled(cron = "0 */3 * * * *")

    @RequestMapping(value = "order/getOrderList.htm", method = RequestMethod.GET)
    public void getOrderList() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        gregorianCalendar.add(GregorianCalendar.HOUR, -2);
        XMLGregorianCalendar lastUpdatedAfter = MwsUtl.getDTF().newXMLGregorianCalendar(gregorianCalendar);
        this.orderApiService.getOrderList(lastUpdatedAfter);
    }    
}
