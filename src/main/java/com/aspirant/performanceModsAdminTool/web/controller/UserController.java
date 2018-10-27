/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.web.controller;

import com.aspirant.performanceModsAdminTool.framework.ApplicationSession;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.Password;
import com.aspirant.performanceModsAdminTool.model.Role;
import com.aspirant.performanceModsAdminTool.model.User;
import com.aspirant.performanceModsAdminTool.service.UserService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.utils.DateUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author gaurao
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/addUser.htm", method = RequestMethod.GET)
    public String showAddUserForm(ModelMap model) {
        ApplicationSession applicationSession = ApplicationSession.getCurrent();
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        User user = new User();
        user.setActive(true);
        model.addAttribute("user", user);

        model.addAttribute("roleList", applicationSession.getCanCreateRoleList(curUser.getRole().getRoleId()));
        return "/admin/addUser";
    }

    @RequestMapping(value = "/admin/addUser.htm", method = RequestMethod.POST)
    public String onAddUserSubmit(@ModelAttribute("user") User user, Errors errors, ModelMap model, HttpServletRequest request) {
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            user = null;
            return "redirect:/admin/userList.htm?msg=msg.actionCancelled";
        } else {
            ApplicationSession applicationSession = ApplicationSession.getCurrent();
            if (this.userService.existUserByUsername(user.getUsername())) {
                errors.rejectValue("username", "msg.duplicateUser");
                model.addAttribute("roleList", applicationSession.getCanCreateRoleList(curUser.getRole().getRoleId()));
                return "/admin/userList";
            }

            int userId = this.userService.addUser(user);
            if (userId == 0) {
                errors.rejectValue("username", "msg.userError");
                model.addAttribute("roleList", applicationSession.getCanCreateRoleList(curUser.getRole().getRoleId()));
                return "/admin/userList";
            } else {
                return "redirect:/admin/userList.htm?msg=msg.userAddedSuccessfully";
            }
        }
    }

    @RequestMapping(value = "/admin/userList.htm", method = RequestMethod.GET)
    public String showUserListPage(ModelMap model, HttpServletRequest request, HttpSession session) {
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        ApplicationSession applicationSession = ApplicationSession.getCurrent();
        String roleId1 = (String) (session.getAttribute("roleId") == null ? "" : session.getAttribute("roleId"));
        List<Role> roleList = this.userService.getRoleList();
        model.addAttribute("roleList", roleList);
        String roleId = ServletRequestUtils.getStringParameter(request, "roleId", roleId1);
//        session.setAttribute("roleId", roleId);
        model.addAttribute("roleList", applicationSession.getCanCreateRoleList(curUser.getRole().getRoleId()));
        model.addAttribute("userList", this.userService.getUserList(false, roleId));
        return "/admin/userList";
    }

    @RequestMapping(value = "/admin/userFailedAttemptsReset.htm")
    public String resetFailedAttempts(HttpServletRequest request) throws UnsupportedEncodingException {
        int userId = ServletRequestUtils.getIntParameter(request, "userId", 0);
        int result = this.userService.resetFailedAttemptsByUserId(userId);
        return "redirect:../admin/userList.htm?msg=" + URLEncoder.encode("Failed attempts reset successfully", "UTF-8");
    }

    @RequestMapping(value = "/admin/updateExpiredPassword.htm", method = RequestMethod.GET)
    public String showUpdatePasswordExpiredForm(ModelMap model) {
        Password password = new Password();
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        LogUtils.systemLogger.info(LogUtils.buildStringForLog("Showing form for UpdateExpiredPassword", GlobalConstants.TAG_SYSTEMLOG));
        password.setUserId(curUser.getUserId());
        password.setUsername(curUser.getUsername());
        model.addAttribute("password", password);
        return "/admin/updateExpiredPassword";
    }

    @RequestMapping(value = "/admin/updateExpiredPassword.htm", method = RequestMethod.POST)
    public String updatePasswordExpiredOnSubmit(final @ModelAttribute("password") Password password, Errors errors) {
        if (!this.userService.confirmPassword(password)) {
            LogUtils.systemLogger.info(LogUtils.buildStringForLog("Nested path=" + errors.getNestedPath(), GlobalConstants.TAG_SYSTEMLOG));
            errors.rejectValue("oldPassword", "msg.oldPasswordNotMatch");
            CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

            password.setUserId(curUser.getUserId());
            password.setUsername(curUser.getUsername());
            return "/admin/updateExpiredPassword";
        } else {
            LogUtils.systemLogger.info(LogUtils.buildStringForLog("Updating user password", GlobalConstants.TAG_SYSTEMLOG));
            this.userService.updatePassword(password, 90);
            // all you need to do now is load the correct Authorities
            Authentication curAuthentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails curUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            curUser.setExpiresOn(DateUtils.getOffsetFromCurrentDateObject(DateUtils.IST, 90));
            curUser.setBusinessFunction(this.userService.getBusinessFunctionsForUserId(curUser.getUserId()));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(curUser, curAuthentication.getCredentials(), curUser.getAuthorities());
            auth.setDetails(curAuthentication.getDetails());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/home/home.htm?msg=msg.passwordUpdated";
        }
    }

    @RequestMapping(value = "/admin/updateExpiredPassword.htm", method = RequestMethod.POST, params = "_cancel")
    public String onUpdateExpiredPasswordCancel() {
        return "redirect:../home/login.htm";
    }

    @RequestMapping(value = "/admin/userPasswordReset.htm")
    public String userPasswordReset(HttpServletRequest request, HttpSession session) throws UnsupportedEncodingException {
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        int userId = ServletRequestUtils.getIntParameter(request, "userId", 0);
        String roleId = (String) (session.getAttribute("roleId"));
        session.setAttribute("roleId", roleId);
        User user = this.userService.getUserByUserId(userId);
        if (!this.userService.canCreateRoleByRoleId(curUser.getRole().getRoleId(), user.getRole().getRoleId())) {
            return "redirect:/admin/userList.htm?msg=msg.cantEditRole";
        } else {
            String pass = this.userService.resetPasswordByUserId(userId);
            if (pass != null) {
                return "redirect:../admin/userList.htm?msg=" + URLEncoder.encode("Your new password is :" + pass, "UTF-8");
            } else {
                return "redirect:../admin/userList.htm?msg=" + URLEncoder.encode("Failed to reset your password.", "UTF-8");
            }
        }
    }

    @RequestMapping("/admin/showEditUser.htm")
    public String showEditUserForm(@RequestParam(value = "userId", required = true) int userId, ModelMap model) {
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        ApplicationSession applicationSession = ApplicationSession.getCurrent();
        User user = this.userService.getUserByUserId(userId);
        if (!this.userService.canCreateRoleByRoleId(curUser.getRole().getRoleId(), user.getRole().getRoleId())) {
            return "redirect:/admin/userList.htm?msg=msg.cantEditRole";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("roleList", applicationSession.getCanCreateRoleList(curUser.getRole().getRoleId()));
            return "/admin/editUser";
        }
    }

    @RequestMapping(value = "/admin/showEditUser.htm", method = RequestMethod.POST)
    public String onEditUserSubmit(@ModelAttribute("user") User user, Errors errors, ModelMap model, HttpServletRequest request) {
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        ApplicationSession applicationSession = ApplicationSession.getCurrent();
        try {
            if (!this.userService.canCreateRoleByRoleId(curUser.getRole().getRoleId(), user.getRole().getRoleId())) {
                return "redirect:/admin/userList.htm?msg=msg.cantEditRole";
            } else {
                // Passed the can create check so go ahead 
                User newUser = this.userService.getUserByUsername(user.getUsername());

                if (newUser != null && newUser.getUserId() != user.getUserId()) {
                    errors.reject("username", "msg.duplicateUser");
                    model.addAttribute("roleList", applicationSession.getCanCreateRoleList(curUser.getRole().getRoleId()));
                    return "/admin/editUser";
                }
                try {
                    this.userService.updateUser(user);
                    return "redirect:/admin/userList.htm?msg=msg.userUpdatedSuccessfully";
                } catch (Exception e) {
                    LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
                    errors.reject("username", "msg.userError");
                    model.addAttribute("roleList", applicationSession.getCanCreateRoleList(curUser.getRole().getRoleId()));
                    return "/admin/editUser";
                }
            }
        } catch (Exception e) {
            return " ";
        }
    }

    @RequestMapping(value = "/admin/showEditUser.htm", method = RequestMethod.POST, params = "_cancel")
    public String onEditUserCancel(@ModelAttribute("user") User user, ModelMap model) {
        user = null;
        return "redirect:/admin/userList.htm?msg=msg.actionCancelled";
    }

    @RequestMapping(value = "/home/changePassword.htm", method = RequestMethod.GET)
    public String showChangePasswordPage(ModelMap model) {
        Password password = new Password();
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        password.setUserId(curUser.getUserId());
        password.setUsername(curUser.getUsername());
        model.addAttribute("password", password);
        return "/home/changePassword";
    }

    @RequestMapping(value = "/home/changePassword.htm", method = RequestMethod.POST)
    public String onChangePasswordSubmit(@ModelAttribute("password") Password password, Errors errors, HttpServletRequest request) {
        String cancel = ServletRequestUtils.getStringParameter(request, "_cancel", null);
        if (cancel != null) {
            password = null;
            return "redirect:/home/home.htm?msg=msg.actionCancelled";
        } else {
            if (!this.userService.confirmPassword(password)) {
                errors.rejectValue("oldPassword", "msg.oldPasswordNotMatch");
                return "/home/changePassword";
            } else {
                this.userService.updatePassword(password, 90);
                return "redirect:/home/home.htm?msg=msg.passwordUpdated";
            }
        }
    }
}
