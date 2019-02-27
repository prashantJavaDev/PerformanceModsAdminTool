/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.service.PremierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author pk
 */
@Controller
public class PremierApiControler {

    @Autowired
    PremierService premierService;

    @RequestMapping("/premier/token.htm")
    public String getPremierToken(){
        premierService.getSessionToken(3);
        return "home/home";
    }
    @RequestMapping("/premier/token2.htm")
    public String getPremierToken2(){
        premierService.getSessionToken(7);
        return "home/home";
    }

    @Scheduled(cron = "0 0 */3 * * *")
    @RequestMapping("/premier/inventory.htm")
    public void getInventory(){
        premierService.getInventory(3);
//        return "home/home";
    }
    
    @Scheduled(cron = "0 0 */4 * * *")
    @RequestMapping("/premier/inventory2.htm")
    public void getInventory2(){
        premierService.getInventory(7);
//        return "home/home";
    }

    @Scheduled(cron = "0 0 10 * * ?")
    @RequestMapping("/premier/price.htm")
    public void getPrice(){
        premierService.getPrice(3);
//        return "home/home";
    }
    @RequestMapping("/premier/price2.htm")
    public String getPrice2(){
        premierService.getPrice(7);
        return "home/home";
    }

}
