/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.service.PremierService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return "home/home";
    }
    
}
