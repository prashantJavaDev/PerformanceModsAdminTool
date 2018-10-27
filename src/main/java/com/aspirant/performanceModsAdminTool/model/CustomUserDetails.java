/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model;

import com.aspirant.utils.DateUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author gaurao
 */
public class CustomUserDetails implements UserDetails, Serializable {

    private int userId;
    private String username;
    private String password;
    private boolean active;
    private boolean expired;
    private int failedAttempts;
    private boolean outsideAccess;
    private Date expiresOn;
    private Date lastLoginDate;
    private Role role;
    private List<SimpleGrantedAuthority> businessFunction;

    public List<SimpleGrantedAuthority> getBusinessFunction() {
        return businessFunction;
    }

    public void setBusinessFunction(List<String> businessFunction) {
        List<SimpleGrantedAuthority> finalBusinessFunction = new ArrayList<SimpleGrantedAuthority>();
        for (String bf : businessFunction) {
            finalBusinessFunction.add((new SimpleGrantedAuthority(bf)));
        }
        this.businessFunction = finalBusinessFunction;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public boolean isOutsideAccess() {
        return outsideAccess;
    }

    public void setOutsideAccess(boolean outsideAccess) {
        this.outsideAccess = outsideAccess;
    }

    public Date getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return businessFunction;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        if (isExpired()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isAccountNonLocked() {
        if (failedAttempts <= 3) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (isActive()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPasswordExpired() {
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMD);
        if (DateUtils.compareDates(DateUtils.formatDate(this.expiresOn, DateUtils.YMD), curDate) > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return "CustomUserDetails{" + "userId=" + userId + ", username=" + username + ", active=" + active + ", expired=" + expired + ", failedAttempts=" + failedAttempts + ", outsideAccess=" + outsideAccess + ", expiresOn=" + expiresOn + ", lastLoginDate=" + lastLoginDate + ", role=" + role + ", businessFunction=" + businessFunction + '}';
    }

}
