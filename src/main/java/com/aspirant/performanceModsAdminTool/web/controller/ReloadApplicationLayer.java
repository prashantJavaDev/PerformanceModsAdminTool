/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.framework.ApplicationSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author gaurao
 */
@Controller
public class ReloadApplicationLayer {

    @RequestMapping("/admin/reloadApplicationLayer")
    public String reloadApplicationLayer() {
        ApplicationSession applicationSession = ApplicationSession.getCurrent();
        applicationSession.reloadAll();
        return "redirect:../home/home.htm?msg=msg.reloadSuccess";
    }
}
