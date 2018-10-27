/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.service.OrderService;
import com.aspirant.performanceModsAdminTool.service.TrackingApiService;
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
public class TrackingController {

    @Autowired
    TrackingApiService trackingApiService;

//    @Scheduled(cron = "0 */15 * * * *")
    @RequestMapping(value = "order/marketplaceTracking.htm", method = RequestMethod.GET)
    public void putMarketplaceTracking() {
        this.trackingApiService.submitFeed();
    }

//    @Scheduled(cron = "0 */10 * * * *")
    @RequestMapping(value = "order/marketplaceOrderAcknowledgement.htm", method = RequestMethod.GET)
    public void putMarketplaceOrderAcknowledgement() {
        this.trackingApiService.submitAcknowledgementFeed();
    }
}
