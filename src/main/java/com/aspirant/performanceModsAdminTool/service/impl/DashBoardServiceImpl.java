/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.DashBoardDao;
import com.aspirant.performanceModsAdminTool.model.Ticket;
import com.aspirant.performanceModsAdminTool.service.DashBoardService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ritesh
 */
@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    private DashBoardDao dashBoardDao;

    @Override
    public Map<String, Object> getOpenTicketDetails() {
        return this.dashBoardDao.getOpenTicketDetails();
    }

    @Override
    public Map<String, Object> getMonthlyTicketDetails() {
        return this.dashBoardDao.getMonthlyTicketDetails();
    }

    @Override
    public Map<String, Object> getMonthlyTatDetails() {
        return this.dashBoardDao.getMonthlyTatDetails();
    }

    @Override
    public Map<String, Object> getTicketTypeTat() {
        return this.dashBoardDao.getTicketTypeTat();
    }

    @Override
    public Map<String, Object> getPercentageFcr() {
        return this.dashBoardDao.getPercentageFcr();
    }

    @Override
    public Map<String, Object> getSummaryForGraph() {
        return this.dashBoardDao.getSummaryForGraph();
    }

    @Override
    public List<Map<String, Object>> getLast7daysUnshippedOrderData() {
        return this.dashBoardDao.getLast7daysUnshippedOrderData();
    }

    @Override
    public Map<String, Object> getOrderShippmentDetails() {
        return this.dashBoardDao.getOrderShippmentDetails();
    }

    @Override
    public Map<String, Object> getPricesDifferenceAndLowCountProduct() {
        return this.dashBoardDao.getPricesDifferenceAndLowCountProduct();
    }

    @Override
    public Map<String, Object> getProductDetailsForWebsite() {
        
        return this.dashBoardDao.getProductDetailsForWebsite();
    }
    
}
