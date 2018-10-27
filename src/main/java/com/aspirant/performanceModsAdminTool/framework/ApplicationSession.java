/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.framework;

import com.aspirant.performanceModsAdminTool.model.Company;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.MainCategory;
import com.aspirant.performanceModsAdminTool.model.Marketplace;
import com.aspirant.performanceModsAdminTool.model.ProductStatus;
import com.aspirant.performanceModsAdminTool.model.Role;
import com.aspirant.performanceModsAdminTool.model.TicketType;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 * @author gaurao
 */
@Component
@Scope("application")
public class ApplicationSession {

    @Autowired
    private ApplicationLoadService applicationLoadService;
    private List<Role> roleList = new LinkedList<Role>();
    private List<MainCategory> mainCategoryListActive = new LinkedList<MainCategory>();
    private List<TicketType> ticketTypeListActive = new LinkedList<TicketType>();
    private List<Company> companyListActive = new LinkedList<Company>();
    private List<Marketplace> marketplaceListActive = new LinkedList<Marketplace>();
    private List<ProductStatus> productStatusListActive = new LinkedList<ProductStatus>();

    public static ApplicationSession getCurrent() {
        return ApplicationContextProvider.getApplicationContext().getBean(ApplicationSession.class);
    }

    /**
     * Method to reload all the most required lists
     *
     */
    public void reloadAll() {
        reloadRoleList();
        reloadMainCategoryList();
        reloadTicketTypeList();
        reloadCompanyList();
        reloadMarketplaceList();
        reloadProductStatusList();
    }

    /**
     * Method to reload role lists
     *
     */
    public void reloadRoleList() {
        CustomUserDetails curUser = (CustomUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        this.roleList = null;
        this.roleList = applicationLoadService.getCanCreateRoleList(curUser.getRole().getRoleId());;
    }

    public List<Role> getCanCreateRoleList(String roleId) {
        if (this.roleList != null && !this.roleList.isEmpty()) {
            return this.roleList;
        } else {
            this.roleList = applicationLoadService.getCanCreateRoleList(roleId);
            return this.roleList;
        }
    }

    public void reloadMainCategoryList() {
        this.mainCategoryListActive = null;
        this.mainCategoryListActive = applicationLoadService.getMainCategoryListActive();
    }

    public List<MainCategory> getMainCategoryListActive() {
        if (this.mainCategoryListActive != null && !this.mainCategoryListActive.isEmpty()) {
            return this.mainCategoryListActive;
        } else {
            this.mainCategoryListActive = applicationLoadService.getMainCategoryListActive();
            return this.mainCategoryListActive;
        }
    }

    public void reloadTicketTypeList() {
        this.ticketTypeListActive = null;
        this.ticketTypeListActive = applicationLoadService.getTicketTypeListActive();
    }

    public List<TicketType> getTicketTypeListActive() {
        if (this.ticketTypeListActive != null && !this.ticketTypeListActive.isEmpty()) {
            return this.ticketTypeListActive;
        } else {
            this.ticketTypeListActive = applicationLoadService.getTicketTypeListActive();
            return this.ticketTypeListActive;
        }
    }

    public void reloadCompanyList() {
        this.companyListActive = null;
        this.companyListActive = applicationLoadService.getCompanyListActive();
    }

    public List<Company> getCompanyListActive() {
        if (this.companyListActive != null && !this.companyListActive.isEmpty()) {
            return this.companyListActive;
        } else {
            this.companyListActive = applicationLoadService.getCompanyListActive();
            return this.companyListActive;
        }
    }

    public void reloadMarketplaceList() {
        this.marketplaceListActive = null;
        this.marketplaceListActive = applicationLoadService.getMarketplaceListActive();
    }

    public List<Marketplace> getMarketplaceListActive() {
        if (this.marketplaceListActive != null && !this.marketplaceListActive.isEmpty()) {
            return this.marketplaceListActive;
        } else {
            this.marketplaceListActive = applicationLoadService.getMarketplaceListActive();
            return this.marketplaceListActive;
        }
    }

    public void reloadProductStatusList() {
        this.productStatusListActive = null;
        this.productStatusListActive = applicationLoadService.getProductStatusListActive();
    }

    public List<ProductStatus> getProductStatusListActive() {
        if (this.productStatusListActive != null && !this.productStatusListActive.isEmpty()) {
            return this.productStatusListActive;
        } else {
            this.productStatusListActive = applicationLoadService.getProductStatusListActive();
            return this.productStatusListActive;
        }
    }

    public TicketType getTicketType(TicketType t) {
        int idx = this.ticketTypeListActive.indexOf(t);
        if (idx >= 0) {
            return this.ticketTypeListActive.get(idx);
        } else {
            return null;
        }
    }
}
