/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

import com.aspirant.performanceModsAdminTool.model.Password;
import com.aspirant.performanceModsAdminTool.model.Role;
import com.aspirant.performanceModsAdminTool.model.User;
import java.util.List;

/**
 *
 * @author gaurao
 */
public interface UserService {

    /**
     * method is used to increase failed attempts if login failed
     *
     * @param username username of user
     * @return Number of rows updated if success, 0 if failed
     */
    public int updateFailedAttemptsByUserId(String username);

    /**
     * method is used to reset failed attempts to 0 if login success
     *
     * @param userId use id of user
     * @return Number of rows updated if success, 0 if failed
     */
    public int resetFailedAttemptsByUserId(int userId);

    /**
     * Method used to check if user with username exists or not
     *
     * @param username to check if exists or not
     * @return true if user exists and false if user does not exists
     */
    public boolean existUserByUsername(String username);

    /**
     * method is used to add new user
     *
     * @param user User Object containing user information
     * @return Generated user's user id if success, 0 if failed
     */
    public int addUser(User user);

    /**
     * method used to get user list based on given parameters
     *
     * @param active active or inactive
     * @param roleId role id based on which we can get user list
     * @return list of users
     */
    public List<User> getUserList(boolean active, String roleId);

    /**
     * Method is used to match old password and new password while password
     * update
     *
     * @param password user password
     * @return true if confirmed, false if error
     */
    public boolean confirmPassword(Password password);

    /**
     * Method is used to update user password with password validity
     *
     * @param password password object containing new password
     * @param offset validity of password in days
     */
    public void updatePassword(Password password, int offset);

    /**
     * method is used to get business function list for user by user Id
     *
     * @param userId user id of user to get business function list
     * @return list of business function
     */
    public List<String> getBusinessFunctionsForUserId(int userId);

    /**
     * method used to check the current logged in user can edit information or
     * not
     *
     * @param roleId roleid of current logged in user
     * @param canCreateRoleId role id of target user
     * @return true if allowed, false if not allowed
     */
    public boolean canCreateRoleByRoleId(String roleId, String canCreateRoleId);

    /**
     * method is used to update user information
     *
     * @param user user object containing updated user information
     */
    public void updateUser(User user);

    /**
     * method used to get object of user by user Id
     *
     * @param userId user id of user
     * @return User object if user id exists else return null
     */
    public User getUserByUserId(int userId);

    /**
     * method used to get object of user object by username
     *
     * @param username username of user
     * @return user object
     */
    public User getUserByUsername(String username);

    /**
     * method used to reset user password by user id
     *
     * @param userId user id of user
     * @return new generated password if success, null if failed
     */
    public String resetPasswordByUserId(int userId);
    
    /**
     * This method is used to get role list
     * 
     * @return list of role
     */
    public List<Role> getRoleList();
}
