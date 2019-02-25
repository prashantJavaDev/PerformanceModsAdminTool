/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.service.MayersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author pk
 */
@Controller
public class MayersController {

    @Autowired
    MayersService mayersService;
    
    @RequestMapping("/mayer/token.htm")
    public String getMayerToken(){
        this.mayersService.getSessionToken();
        return "home/home";
    }
    @RequestMapping("/mayer/item.htm")
    public String getItem(){
        this.mayersService.getInventoryAndPrice();
        return "home/home";
    }
}
