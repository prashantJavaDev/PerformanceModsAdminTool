/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

import com.aspirant.performanceModsAdminTool.model.Company;
import com.aspirant.performanceModsAdminTool.model.Listing;
import com.aspirant.performanceModsAdminTool.model.ShippingCriteria;
import com.aspirant.performanceModsAdminTool.model.ShippingDetails;
import java.util.List;

/**
 *
 * @author shrutika
 */
public interface AdminService {

    /**
     * This method used for mapping of product sub category to main category
     *
     * @param mainCategoryId mainCategory id for selected main category
     * @param assignCategories integer array of sub category ids which will be
     * mapped with main category
     * @return 1-Success, 0-Error
     */
    public int insertCategoryMapping(int mainCategoryId, int[] assignCategories);
    
    public int insertChildCategoryMapping(int subCategoryId, int[] assignChildCategories);
    
    public int insertSubChildCategoryMapping(int childCategoryId, int[] assignSubChildCategories);

    /**
     * Method is used to create new listing for the marketplace. Also it will
     * make this listing available for pricing.
     *
     * @param listing listing object
     * @return 1-Success, 0-Error
     */
    public int addNewListing(Listing listing);

    /**
     * Method is used to generate new sku using provided manufacturer MPN while
     * creating new listing.
     *
     * @param productMpn manufacturer part number
     * @return generated sku
     */
    public String generateSku(String productMpn, int manufacturerId);

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
     * @return Warehouse Shipping Details object
     */
    public ShippingDetails getWarehouseShippingDetailsByDetailsId(int warehouseShippingDetailsId);
}
