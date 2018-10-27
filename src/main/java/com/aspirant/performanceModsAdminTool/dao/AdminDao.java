/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao;

import com.aspirant.performanceModsAdminTool.model.Company;
import com.aspirant.performanceModsAdminTool.model.Customers;
import com.aspirant.performanceModsAdminTool.model.Listing;
import com.aspirant.performanceModsAdminTool.model.ShippingCriteria;
import com.aspirant.performanceModsAdminTool.model.ShippingDetails;
import java.util.List;

/**
 *
 * @author shrutika
 */
public interface AdminDao {

    /**
     * This method used for mapping of product sub category to main category
     *
     * @param mainCategoryId mainCategory id for selected main category
     * @param assignCategories integer array of sub category ids which will be
     * mapped with main category
     */
    public void insertCategoryMapping(int mainCategoryId, int[] assignCategories);
    
    public void insertChildCategoryMapping(int subCategoryId, int[] assignChildCategories);
    
    public void insertSubChildCategoryMapping(int childCategoryId, int[] assignSubChildCategories);
    

    /**
     * Method is used to create new listing for the marketplace. Also it will
     * make this listing available for pricing.
     *
     * @param listing listing object
     */
    public void addNewListing(Listing listing);

    /**
     * Method is used to get list of Skus for provided manufacturer MPN
     *
     * @param productMpn manufacturer part number of the product
     * @return list of skus
     */
    public List<String> getSkuList(String productMpn, int manufacturerId);

    /**
     * Method used to get performanceMods MPN by manufacturer MPN
     *
     * @param productMpn manufacturer part number of the product
     * @return performanceMods MPN
     */
    public String getperformanceModsMpnByMPN(String productMpn);

    /**
     * Method is used to add new company
     *
     * @param company company object
     * @return company id if success and 0 if error
     */
    public int addCompany(Company company);

    /**
     * This method is used to get list of the companies
     *
     * @param active 1 for active companies and 0 for all companies
     * @return list of companies
     */
    public List<Company> getCompanyList(boolean active);

    /**
     * Method is used to get company object by company id
     *
     * @param companyId company id
     * @return company object
     */
    public Company getcompanyBycompanyID(int companyId);

    /**
     * Method will update company information
     *
     * @param company company object
     * @return number of rows updated
     */
    public int updateCompany(Company company);
    
    /**
     * Method is used to get Shipping Criteria List
     * 
     * @return list of Shipping Criteria
     */
    public List<ShippingCriteria> getshippingCriteriaList();
    
    /**
     * This method is used to add Shipping Details
     * 
     * @param shippingDetails shippingDetails object containing shippingDetails information
     * @return shippingDetails id if success and 0 if error
     */
    public int addWarehouseShipping(ShippingDetails shippingDetails, int criteriaId, int warehouseId);
    
    /**
     * Method is used to get Warehouse Shipping Details object by Details Id
     * 
     * @param warehouseShippingDetailsId
     * @return  Warehouse Shipping Details object
     */
    public ShippingDetails getWarehouseShippingDetailsByDetailsId(int warehouseShippingDetailsId);
    
    /**
     * This method is used to add new customer 
     * 
     * @param customer object of customer
     * @return customer id if success and 0 if error
     */
    public int addCustomer(Customers customer);
}
