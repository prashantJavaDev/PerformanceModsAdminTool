/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.UserDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.Password;
import com.aspirant.performanceModsAdminTool.model.Role;
import com.aspirant.performanceModsAdminTool.model.User;
import com.aspirant.performanceModsAdminTool.service.UserService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author gaurao
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int updateFailedAttemptsByUserId(String username) {
        return this.userDao.updateFailedAttemptsByUserId(username);
    }

    @Override
    public int resetFailedAttemptsByUserId(int userId) {
        return this.userDao.resetFailedAttemptsByUserId(userId);
    }

    @Override
    public boolean existUserByUsername(String username) {
        if (this.userDao.getUserByUsername(username) == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int addUser(User user) {
        try {
            return this.userDao.addUser(user);
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public boolean confirmPassword(Password password) {
        return this.userDao.confirmPassword(password);
    }

    @Override
    public void updatePassword(Password password, int offset) {
        this.userDao.updatePassword(password, offset);
    }

    @Override
    public List<String> getBusinessFunctionsForUserId(int userId) {
        return this.userDao.getBusinessFunctionsForUserId(userId);
    }

    @Override
    public boolean canCreateRoleByRoleId(String roleId, String canCreateRoleId) {
        return this.userDao.canCreateRoleByRoleId(roleId, canCreateRoleId);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userDao.getUserByUsername(username);
    }

    @Override
    public void updateUser(User user) {
        try {
            this.userDao.updateUser(user);
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
        }
    }

    @Override
    public User getUserByUserId(int userId) {
        return this.userDao.getUserByUserId(userId);
    }

    @Override
    public List<User> getUserList(boolean active, String roleId) {
        return this.userDao.getUserList(active, roleId);
    }

    @Override
    public String resetPasswordByUserId(int userId) {
        return this.userDao.resetPasswordByUserId(userId);
    }

    @Override
    public List<Role> getRoleList() {
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return this.userDao.getCanCreateRoleList(curUser.getRole().getRoleId());
    }
}
