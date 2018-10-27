/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.security;

import com.aspirant.performanceModsAdminTool.dao.UserDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
//import com.aspirantutils.IPUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author Shrutika
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    private Set<String> allowedIpRange;

    public CustomUserDetailsService() {
        this.allowedIpRange = new HashSet<String>();
        this.allowedIpRange.addAll(Arrays.asList(GlobalConstants.ALLOWED_IP_RANGE));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String ipAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
        try {
            CustomUserDetails user = this.userDao.getUserDetailsByUsername(username);
            if (!user.isActive()) {
                LogUtils.systemLogger.warn(LogUtils.buildStringForLog(ipAddress, username, "Account disabled", GlobalConstants.TAG_ACCESSLOG));
            } else if (!user.isAccountNonLocked()) {
                LogUtils.systemLogger.warn(LogUtils.buildStringForLog(ipAddress, username, "Account locked", GlobalConstants.TAG_ACCESSLOG));
            } else if (!(user.isOutsideAccess() || checkIfIpIsFromAllowedRange(ipAddress))) {
                user.setActive(false);
                LogUtils.systemLogger.warn(LogUtils.buildStringForLog(ipAddress, username, "Outside access", GlobalConstants.TAG_ACCESSLOG));
            } else {
                if (user.isPasswordExpired()) {
                    // only insert the ROLE_BF_PASSWORD_EXPIRED
                    LogUtils.systemLogger.debug(LogUtils.buildStringForLog("Credentials are Expired so only put in ROLE_BF_PASSWORD_EXPIRED into Authoirites", GlobalConstants.TAG_SYSTEMLOG));
                    List<String> businessFunctions = new LinkedList<String>();
                    businessFunctions.add("ROLE_BF_PASSWORD_EXPIRED");
                    user.setBusinessFunction(businessFunctions);
                } else {
                    user.setBusinessFunction(this.userDao.getBusinessFunctionsForUserId(user.getUserId()));
                }
            }
            return user;
        } catch (EmptyResultDataAccessException erda) {
            throw new UsernameNotFoundException("Username not found");
        }
    }

    private boolean checkIfIpIsFromAllowedRange(String ipToCheck) {
        return false;
    }
}
