/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service;

import com.aspirant.performanceModsAdminTool.model.ChildOfChildCategory;
import com.aspirant.performanceModsAdminTool.model.ChildOfSubCategory;
import com.aspirant.performanceModsAdminTool.model.Company;
import com.aspirant.performanceModsAdminTool.model.DTO.MarketplaceListingSkuDTO;
import com.aspirant.performanceModsAdminTool.model.EmailerBlock;
import com.aspirant.performanceModsAdminTool.model.Manufacturer;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.Product;
import com.aspirant.performanceModsAdminTool.model.ProductDetails;
import com.aspirant.performanceModsAdminTool.model.ProductStatus;
import com.aspirant.performanceModsAdminTool.model.SubCategory;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import java.util.List;
import java.util.Map;

/**
 *
 * @author shrutika
 */
public interface ProductService {

    /**
     * This method is used to get list of active manufacturers.
     *
     * @return List of manufacturer
     */
    public List<Manufacturer> getListOfManufacturer();

    /**
     * This method is used to get list of all active Sub Categories.
     *
     * @return List of Sub Category
     */
    public List<SubCategory> getListOfSubCategory();

    /**
     * This method is used to get list of all active child categories.
     *
     * @return
     */
    public List<ChildOfSubCategory> getListOfChildCategory();

    /**
     * This method is used to get list of all active Sub child categories.
     *
     * @return
     */
    public List<ChildOfChildCategory> getListOfSubChildCategory();

    /**
     * This method is used to get list of active sub category by selected main
     * category
     *
     * @param mainCategoryId Main category id
     * @return List of Sub Category
     */
    public List<SubCategory> getSubCategoryListForMainCategory(int mainCategoryId);

    /**
     * This method is used to get list of sub category ids by main category id
     *
     * @param mainCategoryId main category Id
     * @return list of sub category ids
     */
    public List<Integer> getSubCategoryIdListByMainCategory(int mainCategoryId);

    public List<Integer> getChildCategoryIdListBySubCategory(int subCategoryId);

    public List<Integer> getSubChildCategoryIdListByChildCategory(int childCategoryId);

    /**
     * Method used to add new product manually and generate performanceMods MPN
     *
     * @param product product object
     * @return product id
     */
    public int addProduct(Product product);

    /**
     * Method used to get list of all products based on given parameters
     *
     * @param productStatusId product status id
     * @param manufacturerId manufacturer id
     * @param performanceModsMpn performanceMods MPN
     * @param productName product name
     * @param pageNo page number for pagination
     * @param warehouseMpn warehouse MPN
     * @param productMpn manufacturer MPN
     * @return list of products
     */
    public List<Product> getProductList(int productStatusId, int manufacturerId, String performanceModsMpn, String productName, int pageNo, String warehouseMpn, String productMpn, String startDate, String stopDate);

    /**
     * Method used to get count of all products for pagination
     *
     * @param productStatusId product status id
     * @param manufacturerId manufacturer id
     * @param performanceModsMpn performanceMods MPN
     * @param productName product name
     * @param warehouseMpn warehouse MPN
     * @param productMpn manufacturer MPN
     * @return count of products
     */
    public int getProductListCount(int productStatusId, int manufacturerId, String performanceModsMpn, String productName, String warehouseMpn, String productMpn, String startDate, String stopDate);

    /**
     * Method used to get product list of all products for export to excel
     *
     * @param productStatusId product status id
     * @param manufacturerId manufacturer id
     * @param performanceModsMpn performanceMods MPN
     * @param productName product name
     * @param warehouseMpn warehouse MPN
     * @param productMpn manufacturer MPN
     * @return list of products
     */
    public List<Product> getProductListForExcel(int productStatusId, int manufacturerId, String performanceModsMpn, String productName, String warehouseMpn, String productMpn);

    /**
     * Method is used to get product object by product id
     *
     * @param productId product id
     * @return product object
     */
    public Product getProductById(int productId);

    /**
     * Method is used to update product information
     *
     * @param product product object
     */
    public void updateProduct(Product product);

    /**
     * Method used to add sub category
     *
     * @param subCategoryDesc description for the sub category
     * @param active sub category status active/inactive
     * @return sub category id
     */
    public int addSubCategory(String subCategoryDesc, boolean active);

    /**
     * Method used to add child category
     *
     * @param childCategoryDesc description for the child category
     * @param active sub category status active/inactive
     * @return sub sub category id
     */
    public int addChildCategory(String childCategoryDesc, boolean active);

    /**
     * Method is used to search performanceMods MPN for autocomplete
     *
     * @param term input string for search
     * @return list of performanceMods MPNs
     */
    public List<String> searchMpn(String term);

    /**
     * Method is used to search warehouse MPN for autocomplete
     *
     * @param term input string for search
     * @return list of warehouse MPNs
     */
    public List<String> searchWarehouseMpn(String term);

    /**
     * Method is used to search manufacturer MPN for autocomplete
     *
     * @param term input string for search
     * @return list of manufacturer MPNs
     */
    public List<String> searchProductMpn(String term);

    /**
     * Method used to search product name for autocomplete
     *
     * @param term input string for search
     * @return list of product names
     */
    public List<String> searchProductName(String term);

    /**
     * Method used to get different warehouse details of product based on
     * performanceMods MPN
     *
     * @param performanceModsMpn performanceMods MPN
     * @return list of products
     */
    public List<ProductDetails> getProductListWarehouseWise(String performanceModsMpn);

    /**
     * Method is used to get product information by performanceMods MPN
     *
     * @param performanceModsMpn performanceMods MPN
     * @return product object
     */
    public Product getProductInfoByperformanceModsMpn(String performanceModsMpn);

    /**
     * Method is used to get performanceMods MPN by manufacturer MPN and manufacturer id
     * to display performanceMods MPN while duplicate product exception
     *
     * @param manufacturerMpn manufacturer MPN
     * @param manufacturerId manufacturer id
     * @return performanceMods MPN
     */
    public String getperformanceModsMpnByManufacturerMpn(String manufacturerMpn, int manufacturerId);

    /**
     * Method is used to get list of skus and marketplace listing ids mapped
     * with given performanceMods MPN
     *
     * @param performanceModsMpn performanceMods MPN
     * @return list of skus and marketplace listing ids
     */
    public List<MarketplaceListingSkuDTO> getMarketplaceListingsAndSku(String performanceModsMpn);

    /**
     * method return list of product list containing low count
     *
     * @param count
     * @return
     */
    public List<Order> getLowCountProductForDashbored(int count);

    /**
     * method will return list of product containing null data
     *
     * @param productId
     * @return
     */
    public List<Product> getMissingProductDataList(int pageNo);

    /**
     * get number of rows in the list
     *
     * @return
     */
    public int getMissingProductDataListCount();

    /**
     * this method return list of particular performanceModsMpn product to delete
     *
     * @param performanceModsMpn
     * @return
     */
    public List<Map<String, Object>> getProductListForDelet(String marketplaceMpn);

    /**
     * this method will delete the product by product ID
     *
     * @param productId
     * @return
     */
    public int deleteProductByProductID(String warehouse_part_num);
    
    public int deleteListingBySku(String sku);

    /**
     * This method is used to get list of active child categories by selected
     * sub category
     *
     * @param subCategoryId
     * @return
     */
    public List<ChildOfSubCategory> getChildCategoryListForSubCategory(int subCategoryId);

    /**
     * This method is used to get list of active sub-child categories by
     * selected child category
     *
     * @param childCategoryId
     * @return
     */
    public List<ChildOfChildCategory> getsubChildCategoryListForSubCategory(int childCategoryId);

    /**
     * get list for product download
     *
     * @param productStatusId
     * @param manufacturerId
     * @param productName
     * @param pageNo
     * @param mainCategoryId
     * @param subCategoryId
     * @param childCategoryId
     * @return
     */
    public List<Product> getDownloadProductList(int productStatusId, int manufacturerId, String productName, int pageNo, int mainCategoryId, int subCategoryId, int childCategoryId);

    public int getCountDownloadProductList(int productStatusId, int manufacturerId, String productName, int pageNo, int mainCategoryId, int subCategoryId, int childCategoryId);

    public List<SubCategory> getAllSubCategoryList();

    public List<ChildOfSubCategory> getAllChildCategoryList();

    public int addSubChildCategory(String subChildCategoryDesc, boolean active);

    public int saveMultipartFileDataForWebsiteProduct(UploadFeed uploadFeed, int companyId);

    public int UploadImageServiceImpl(UploadFeed uploadFeed);

    public List<Company> getCompanyList(boolean active);

    
}
