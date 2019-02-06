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

    @Scheduled(cron = "0 0 4 ? * SUN")
    @RequestMapping("turn/items.htm")
    public void getitemsOfTurn() {
        turnService.getItems(1);
//        return "home/home";
    }
//    @Scheduled(cron = "0 0 4 ? * SUN")
    @RequestMapping("turn/itemsT2.htm")
    public void getitemsOfTurnt2() {
        turnService.getItems(8);
//        return "home/home";
    }

    @Scheduled(cron = "0 0 12 * * ?")
    @RequestMapping("turn/prices.htm")
    public void getPriceOfTurn() {
        turnService.getPrice(1);
        //return null;
    }
//    @Scheduled(cron = "0 0 12 * * ?")
    @RequestMapping("turn/pricesT2.htm")
    public void getPriceOfTurnT2() {
        turnService.getPrice(8);
        //return null;
    }

    @Scheduled(cron = "0 0 */3 * * *")
    @RequestMapping("turn/inventory.htm")
    public void getInventoryOfTurn() {
        turnService.getInventory(1);
//        return "home/home";
    }
//    @Scheduled(cron = "0 0 */3 * * *")
    @RequestMapping("turn/inventoryT2.htm")
    public void getInventoryOfTurnT2() {
        turnService.getInventory(8);
//        return "home/home";
    }

    @RequestMapping("turn/token.htm")
    public String gettokenOfTurn() {
        turnService.getApiTokenOfTurn(1);
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
