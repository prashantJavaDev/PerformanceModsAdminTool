/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.service.TurnService;
import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping("turn/items.htm")
    public String getitemsOfTurn() {
        turnService.getItems();
        return "home/home";
    }

    @RequestMapping("turn/prices.htm")
    public String getPriceOfTurn() {
        turnService.getPrice();
        return "home/home";
    }

    @RequestMapping("turn/inventory.htm")
    public String getInventoryOfTurn() {
        turnService.getInventory();
        return "home/home";
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
