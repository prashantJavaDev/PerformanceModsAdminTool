/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.dao.IngramDao;
import com.aspirant.performanceModsAdminTool.service.IngramService;
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
public class IngramController {

    @Autowired
    IngramService ingramService;
    @Autowired
    IngramDao ingramDao;

    //@Scheduled(cron = "0 0 13 * * *")
    @RequestMapping(value = "ingram/getIngramPNA.htm", method = RequestMethod.GET)
    public void getPNAFromIngram() {
        this.ingramService.flushDataForIngramPNA();
        this.ingramService.getPNAResponcedata();
    }

//    @Scheduled(cron = "0 */10 * * * *")
    @RequestMapping(value = "ingram/getIngramOrderStatus.htm", method = RequestMethod.GET)
    public void getOrderStatusFromIngram() {
        this.ingramService.getOrderStatusRequestXml();
    }

//    @Scheduled(cron = "0 */15 * * * *")
    @RequestMapping(value = "ingram/getIngramOrderTrackig.htm", method = RequestMethod.GET)
    public void getOrderTrackingFromIngram() {
        this.ingramService.getOrderTrackingRequestXml();
    }

    @RequestMapping(value = "ingram/getIngramOrderDetail.htm", method = RequestMethod.GET)
    public void getOrderDetailFromIngram() {
        this.ingramService.getOrderDetailRequestXml();
    }

    @RequestMapping(value = "ingram/getIngramInvoice.htm", method = RequestMethod.GET)
    public void getInvoiceFromIngram() {
        this.ingramService.getInvoiceXml();
    }

    @RequestMapping(value = "ingram/getIngramShippingForOrder.htm", method = RequestMethod.GET)
    public void getOrderShippingCostFromIngram() {
        this.ingramService.getOrderShippingCostXml();
    }
}
