/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.dao.ListingDao;
import com.aspirant.performanceModsAdminTool.model.Listing;
import com.aspirant.performanceModsAdminTool.service.FeesApiService;
import com.aspirant.performanceModsAdminTool.service.OrderService;
import com.amazonservices.mws.products.model.GetMyFeesEstimateRequest;

import java.util.List;
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
public class FeesController {

    @Autowired
    FeesApiService feesApiService;
    @Autowired
    ListingDao listingDao;

    @Scheduled(cron = "0 0 10,21 * * *")
    @RequestMapping(value = "order/getMarketplaceFees.htm", method = RequestMethod.GET)
    public void getFees() {
        this.listingDao.flushFeesStatus();
        this.feesApiService.getMyFeesEstimate();
    }
}
