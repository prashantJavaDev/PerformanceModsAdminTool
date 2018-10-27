/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Akil Mahimwala
 */
@Controller
@RequestMapping("/errors/accessDenied.htm")
public class AccessDeniedController {

    @RequestMapping(method = RequestMethod.GET)
    public String showPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            if (auth.isAuthenticated()) {
                CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                if (curUser.getBusinessFunction().contains(new SimpleGrantedAuthority("ROLE_BF_PASSWORD_EXPIRED"))) {
                    return "redirect:/admin/updateExpiredPassword.htm";
                } else {
                    return "/errors/accessDenied";
                }
            }
        }
        return "redirect:/home/login.htm";
    }
}
