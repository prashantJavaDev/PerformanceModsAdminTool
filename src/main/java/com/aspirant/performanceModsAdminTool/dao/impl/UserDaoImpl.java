/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.UserDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.Password;
import com.aspirant.performanceModsAdminTool.model.Role;
import com.aspirant.performanceModsAdminTool.model.User;
import com.aspirant.performanceModsAdminTool.model.rowmapper.CustomUserDetailsRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.RoleRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.UserRowMapper;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *
 * @author gaurao
 */
@Repository("userDao")
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public CustomUserDetails getUserDetailsByUsername(String username) {
        String sql = "SELECT u.*, r.* FROM `user` u"
                + " LEFT JOIN `user_role` ur ON u.`USER_ID`=ur.`USER_ID`"
                + " LEFT JOIN  role r ON ur.`ROLE_ID`=r.`ROLE_ID`"
                + " WHERE u.`USERNAME`=?";
        Object params[] = new Object[]{username};
        String ipAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
        try {
            CustomUserDetails u = this.jdbcTemplate.queryForObject(sql, params, new CustomUserDetailsRowMapper());
            return u;
        } catch (EmptyResultDataAccessException erda) {
            LogUtils.systemLogger.warn(LogUtils.buildStringForLog(ipAddress, username, "No User found with username :" + erda, GlobalConstants.TAG_SYSTEMLOG));
            return null;
        }
    }

    @Override
    public int updateFailedAttemptsByUserId(String username) {
        try {
            String sqlQuery = "UPDATE `user` SET FAILED_ATTEMPTS=FAILED_ATTEMPTS+1 WHERE USERNAME=?";
           // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlQuery, GlobalConstants.TAG_SYSTEMLOG));
            return this.jdbcTemplate.update(sqlQuery, username);
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog("Could not update failed attempts " + e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public int resetFailedAttemptsByUserId(int userId) {
        try {
            Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
            String sqlreset = "UPDATE `user` SET FAILED_ATTEMPTS=0,LAST_LOGIN_DATE=? WHERE USER_ID=?";
            return this.jdbcTemplate.update(sqlreset, curDt, userId);
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog("Could not reset failed attempts " + e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public List<Role> getCanCreateRoleList(String roleId) {
        String sqlString = "SELECT role.* from can_create_roles LEFT JOIN role on can_create_roles.CAN_CREATE_ROLE=role.ROLE_ID where can_create_roles.ROLE_ID=? ORDER BY role.`ROLE_ID`";

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, new Object[]{roleId}, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sqlString, new RoleRowMapper(), roleId);
    }

    @Override
    public User getUserByUsername(String username) {
        String sqlString = "SELECT user.*, user_role.ROLE_ID, role.ROLE_NAME FROM user LEFT JOIN user_role ON user.USER_ID=user_role.USER_ID LEFT JOIN role ON user_role.ROLE_ID=role.ROLE_ID WHERE user.USERNAME=?";

        Object params[] = new Object[]{username};
        try {
           // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
            return this.jdbcTemplate.queryForObject(sqlString, params, new UserRowMapper());
        } catch (EmptyResultDataAccessException erda) {
            LogUtils.systemLogger.warn(LogUtils.buildStringForLog("No User found with username", GlobalConstants.TAG_SYSTEMLOG));
            return null;
        }
    }

    @Override
    @Transactional
    public int addUser(User user) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        try {
            SimpleJdbcInsert userInsert = new SimpleJdbcInsert(this.dataSource).withTableName("user").usingGeneratedKeyColumns("USER_ID");
            Map<String, Object> params = new HashMap<String, Object>();
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashPass = encoder.encode(user.getPassword());
            params.put("USERNAME", user.getUsername());
            params.put("PASSWORD", hashPass);
            params.put("EMAIL",user.getEmailId());
            params.put("ACTIVE", user.isActive());
            params.put("EXPIRED", false);
            params.put("EXPIRES_ON", DateUtils.getOffsetFromCurrentDateObject(DateUtils.IST, -1));
            params.put("FAILED_ATTEMPTS", 0);
            params.put("OUTSIDE_ACCESS", user.isOutsideAccess());
            params.put("CREATED_BY", curUser);
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser);
            params.put("LAST_MODIFIED_DATE", curDate);

           // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Inser into user : ", params, GlobalConstants.TAG_SYSTEMLOG));
            int userId = userInsert.executeAndReturnKey(params).intValue();

            String sqlString = "INSERT INTO user_role (USER_ID, ROLE_ID) VALUES(?, ?)";

           // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, new Object[]{userId, user.getRole().getRoleId()}, GlobalConstants.TAG_SYSTEMLOG));
            this.jdbcTemplate.update(sqlString, userId, user.getRole().getRoleId());
            return userId;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean confirmPassword(Password password) {
        String sqlString = "SELECT user.PASSWORD FROM user WHERE user.USER_ID=?";

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, new Object[]{password.getUserId()}, GlobalConstants.TAG_SYSTEMLOG));
        String hash = this.jdbcTemplate.queryForObject(sqlString, String.class, password.getUserId());
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        if (encoder.matches(password.getOldPassword(), hash)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updatePassword(Password password, int offset) {
        try {
            Date offsetDate = DateUtils.getOffsetFromCurrentDateObject(DateUtils.IST, offset);
            String sqlString = "UPDATE user SET PASSWORD=?, EXPIRES_ON=? WHERE user.USER_ID=?";
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String hash = encoder.encode(password.getNewPassword());
            Object params[] = new Object[]{hash, offsetDate, password.getUserId()};

           // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
            this.jdbcTemplate.update(sqlString, params);
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog("Update Password Error " + e, GlobalConstants.TAG_SYSTEMLOG));
        }
    }

    @Override
    public List<String> getBusinessFunctionsForUserId(int userId) {
        String sqlString = "SELECT BUSINESS_FUNCTION_ID FROM user_role LEFT JOIN role_business_function ON user_role.ROLE_ID=role_business_function.ROLE_ID WHERE user_role.USER_ID=?";
        return this.jdbcTemplate.queryForList(sqlString, new Object[]{userId}, String.class);
    }

    @Override
    public boolean canCreateRoleByRoleId(String roleId, String canCreateRoleId) {
        String sqlString = "SELECT count(*) from can_create_roles where can_create_roles.ROLE_ID=? and can_create_roles.CAN_CREATE_ROLE=?";
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, new Object[]{roleId, canCreateRoleId}, GlobalConstants.TAG_SYSTEMLOG));
        int i = this.jdbcTemplate.queryForObject(sqlString, Integer.class, roleId, canCreateRoleId).intValue();
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        String sqlString;
        Object params[];
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        try{
        sqlString = "UPDATE user SET USERNAME=?,EMAIL_ID=?, ACTIVE=?, OUTSIDE_ACCESS=?,LAST_MODIFIED_BY=?,LAST_MODIFIED_DATE=?  WHERE USER_ID=?";
        params = new Object[]{user.getUsername(),user.getEmailId(), user.isActive(), user.isOutsideAccess(), curUser, curDate, user.getUserId()};
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
        this.jdbcTemplate.update(sqlString, params);
        sqlString = "DELETE FROM user_role WHERE USER_ID=?";
        params = new Object[]{user.getUserId()};
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
        this.jdbcTemplate.update(sqlString, user.getUserId());
        sqlString = "INSERT INTO user_role (USER_ID, ROLE_ID) VALUES(?, ?)";
        params = new Object[]{user.getUserId(), user.getRole().getRoleId()};
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
        this.jdbcTemplate.update(sqlString, params);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserByUserId(int userId) {
        String sqlString = "SELECT user.*, user_role.ROLE_ID, role.ROLE_NAME FROM user LEFT JOIN user_role ON user.USER_ID=user_role.USER_ID LEFT JOIN role ON user_role.ROLE_ID=role.ROLE_ID WHERE user.USER_ID=?";
        Object params[] = new Object[]{userId};
        try {
           // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
            return this.jdbcTemplate.queryForObject(sqlString, params, new UserRowMapper());
        } catch (EmptyResultDataAccessException erda) {
            LogUtils.systemLogger.warn(LogUtils.buildStringForLog("No User found with userId", GlobalConstants.TAG_SYSTEMLOG));
            return null;
        }
    }

    @Override
    public List<User> getUserList(boolean active, String roleId) {
        String sql = "SELECT user.*, user_role.ROLE_ID, role.ROLE_NAME FROM user "
                + " LEFT JOIN user_role ON user.USER_ID=user_role.USER_ID "
                + " LEFT JOIN role ON user_role.ROLE_ID=role.ROLE_ID"
                + " where user.ACTIVE";
        Map<String, Object> params = new HashMap<String, Object>();

        if (roleId != null && roleId != "") {
            sql += " AND user_role.ROLE_ID=:roleId ";
            params.put("roleId", roleId);
        }

        sql += " ORDER BY user.`LAST_MODIFIED_DATE` DESC";

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new UserRowMapper());
    }

    @Override
    public String resetPasswordByUserId(int userId) {
        try {
            int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            String pass = RandomStringUtils.randomAlphanumeric(5);
            Date curDt = DateUtils.getCurrentDateObject(DateUtils.IST);
            Date offsetDate = DateUtils.getOffsetFromCurrentDateObject(DateUtils.IST, -1);
            String sqlString = "UPDATE user SET PASSWORD=?, FAILED_ATTEMPTS=0, EXPIRES_ON=?, LAST_MODIFIED_BY=?, LAST_MODIFIED_DATE=? WHERE user.USER_ID=?";
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String hash = encoder.encode(pass);
            Object params[] = new Object[]{hash, offsetDate, curUser, curDt, userId};
            this.jdbcTemplate.update(sqlString, params);
            return pass;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog("Could not reset password : " + e, GlobalConstants.TAG_SYSTEMLOG));
            return null;
        }
    }
}
