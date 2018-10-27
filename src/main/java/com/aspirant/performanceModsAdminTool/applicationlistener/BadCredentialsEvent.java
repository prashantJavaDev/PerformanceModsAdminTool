/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.applicationlistener;

import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.service.UserService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 *
 * @author gaurao
 */
public class BadCredentialsEvent implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        String name = (String) e.getAuthentication().getPrincipal();
        this.userService.updateFailedAttemptsByUserId(name);
        LogUtils.systemLogger.info(LogUtils.buildStringForLog(((WebAuthenticationDetails) e.getAuthentication().getDetails()).getRemoteAddress(), name, "Incorrect password", GlobalConstants.TAG_ACCESSLOG));
    }
}
