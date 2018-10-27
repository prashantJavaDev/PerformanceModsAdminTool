/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.framework;

import com.aspirant.performanceModsAdminTool.dao.AdminDao;
import com.aspirant.performanceModsAdminTool.dao.MarketplaceDao;
import com.aspirant.performanceModsAdminTool.dao.ProductDao;
import com.aspirant.performanceModsAdminTool.dao.TicketDao;
import com.aspirant.performanceModsAdminTool.dao.UserDao;
import com.aspirant.performanceModsAdminTool.model.Company;
import com.aspirant.performanceModsAdminTool.model.MainCategory;
import com.aspirant.performanceModsAdminTool.model.Marketplace;
import com.aspirant.performanceModsAdminTool.model.ProductStatus;
import com.aspirant.performanceModsAdminTool.model.Role;
import com.aspirant.performanceModsAdminTool.model.TicketPriority;
import com.aspirant.performanceModsAdminTool.model.TicketType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author gaurao
 */
@Service
public class ApplicationLoadService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private MarketplaceDao marketplaceDao;

    @Transactional
    public void fetchData() {
        ApplicationSession applicationSession = ApplicationSession.getCurrent();
    }

    /**
     * method is used to get list of roles which current logged in user can
     * create
     *
     * @param roleId role id of current logged in user
     * @return list of roles which current logged in user can create
     */
    public List<Role> getCanCreateRoleList(String roleId) {
        return this.userDao.getCanCreateRoleList(roleId);
    }

    /**
     * This method is used to get list of all active main categories.
     *
     * @return list of main categories
     */
    public List<MainCategory> getMainCategoryListActive() {
        return this.productDao.getListOfMainCategory();
    }

    /**
     * method is used to get ticket type list
     *
     * @return list of ticket types
     */
    public List<TicketType> getTicketTypeListActive() {
        return this.ticketDao.getTicketTypeList();
    }

    /**
     * This method is used to get list of active companies
     *
     * @return list of companies
     */
    public List<Company> getCompanyListActive() {
        return this.adminDao.getCompanyList(true);
    }

    /**
     * This method is used to get list of active marketplaces
     *
     * @return list of marketplaces
     */
    public List<Marketplace> getMarketplaceListActive() {
        return this.marketplaceDao.getMarketplaceList(true);
    }

    /**
     * This method is used to get list of active product statuses
     *
     * @return list of product statuses
     */
    public List<ProductStatus> getProductStatusListActive() {
        return this.productDao.getListOfProductStatus();
    }
}
