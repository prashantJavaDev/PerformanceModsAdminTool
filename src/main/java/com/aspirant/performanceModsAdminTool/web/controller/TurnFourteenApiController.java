/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.service.TurnService;
import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author pk
 */
@Controller
public class TurnFourteenApiController {

    @Autowired
    TurnService turnService;

    @RequestMapping("turn/location.htm")
    public String getLocationOfTurn() {
        turnService.getLocation();
        return "home/home";
    }

    @Scheduled(cron = "0 0 */3 * * *")
//    @RequestMapping("turn/items.htm")
    public void getitemsOfTurn() {
        turnService.getItems();
//        return "home/home";
    }

    @Scheduled(cron = "0 0 12 * * ?")
//    @RequestMapping("turn/prices.htm")
    public void getPriceOfTurn() {
        turnService.getPrice();
        //return null;
    }

    @Scheduled(cron = "0 0 */3 * * *")
//    @RequestMapping("turn/inventory.htm")
    public void getInventoryOfTurn() {
        turnService.getInventory();
//        return "home/home";
    }

    @RequestMapping("turn/token.htm")
    public String gettokenOfTurn() {
        turnService.getApiTokenOfTurn();
        return "home/home";
    }

    @RequestMapping("turn/itemFile.htm")
    public String getItemFileOfTurn() {
        turnService.addItemByFile();
        return "home/home";
    }

    @RequestMapping("turn/priceFile.htm")
    public String getPriceFileOfTurn() {
        turnService.addPriceByFile();
        return "home/home";
    }

    @RequestMapping("turn/inventoryFile.htm")
    public String getinventoryFileOfTurn() {
        turnService.addInventoryByFile();
        return "home/home";
    }
}
