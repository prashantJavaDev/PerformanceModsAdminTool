/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.applicationlistener;

import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.service.UserService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 *
 * @author gaurao
 */
public class SuccessEvent implements ApplicationListener<AuthenticationSuccessEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent e) {
        CustomUserDetails cud = (CustomUserDetails) e.getAuthentication().getPrincipal();
        this.userService.resetFailedAttemptsByUserId(cud.getUserId());
        LogUtils.systemLogger.info(LogUtils.buildStringForLog(((WebAuthenticationDetails) e.getAuthentication().getDetails()).getRemoteAddress(), cud.getUsername(), "Success", GlobalConstants.TAG_ACCESSLOG));
    }
}
