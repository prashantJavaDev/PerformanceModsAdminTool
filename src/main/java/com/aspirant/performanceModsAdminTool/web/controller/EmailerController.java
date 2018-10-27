/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.service.EmailerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Ritesh
 */
@Controller
public class EmailerController {

    @Autowired
    private EmailerService emailerService;

    @Scheduled(cron = "0 */1 * * * *")
    public void sendEmail() {
//        try {
//            this.emailerService.sendEmail();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }
}
