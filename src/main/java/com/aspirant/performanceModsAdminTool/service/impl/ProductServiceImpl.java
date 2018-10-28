/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.service.impl;

import com.aspirant.performanceModsAdminTool.dao.ProductDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.ChildOfChildCategory;
import com.aspirant.performanceModsAdminTool.model.ChildOfSubCategory;
import com.aspirant.performanceModsAdminTool.model.Company;
import com.aspirant.performanceModsAdminTool.model.DTO.MarketplaceListingSkuDTO;
import com.aspirant.performanceModsAdminTool.model.EmailerBlock;
import com.aspirant.performanceModsAdminTool.model.Manufacturer;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.Product;
import com.aspirant.performanceModsAdminTool.model.ProductDetails;
import com.aspirant.performanceModsAdminTool.model.SubCategory;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import com.aspirant.performanceModsAdminTool.service.ProductService;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author shrutika
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    public static final String WEBSITE_PRODUCT = "/home/altius/performanceMods/website_product/";
    public static final String WEBSITE_IMAGE = "/home/altius/performanceMods/website_image/";

    @Override
    public List<Manufacturer> getListOfManufacturer() {
        List<Manufacturer> manufacturerList = this.productDao.getListOfManufacturer();
        return manufacturerList;
    }

    @Override
    public List<SubCategory> getListOfSubCategory() {
        return this.productDao.getListOfSubCategory();
    }

    @Override
    public List<SubCategory> getSubCategoryListForMainCategory(int mainCategoryId) {
        List<SubCategory> subCategoryList = this.productDao.getSubCategoryListForMainCategory(mainCategoryId);
        return subCategoryList;
    }

    @Override
    public List<Integer> getSubCategoryIdListByMainCategory(int mainCategoryId) {
        List<Integer> subCategoryList = this.productDao.getSubCategoryIdListByMainCategory(mainCategoryId);
        return subCategoryList;
    }

    @Override
    public int addProduct(Product product) {
        return this.productDao.addProduct(product);
    }

    @Override
    public List<Product> getProductList(int productStatusId, int manufacturerId, String performanceModsMpn, String productName, int pageNo, String warehouseMpn, String productMpn, String startDate, String stopDate) {
        return this.productDao.getProductList(productStatusId, manufacturerId, performanceModsMpn, productName, pageNo, warehouseMpn, productMpn, startDate, stopDate);
    }

    @Override
    public int getProductListCount(int productStatusId, int manufacturerId, String performanceModsMpn, String productName, String warehouseMpn, String productMpn, String startDate, String stopDate) {
        return this.productDao.getProductListCount(productStatusId, manufacturerId, performanceModsMpn, productName, warehouseMpn, productMpn, startDate, stopDate);
    }

    @Override
    public Product getProductById(int productId) {
        return this.productDao.getProductById(productId);
    }

    @Override
    public void updateProduct(Product product) {
        try {
            this.productDao.updateProduct(product);
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
        }
    }

    @Override
    public int addSubCategory(String subCategoryDesc, boolean active) {
        try {
            return this.productDao.addSubCategory(subCategoryDesc, active);
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public List<String> searchMpn(String term) {
        return this.productDao.searchMpn(term);
    }

    @Override
    public List<String> searchProductName(String term) {
        return this.productDao.searchProductName(term);
    }

    @Override
    public List<ProductDetails> getProductListWarehouseWise(String performanceModsMpn) {
        return this.productDao.getProductListWarehouseWise(performanceModsMpn);
    }

    @Override
    public Product getProductInfoByperformanceModsMpn(String performanceModsMpn) {
        return this.productDao.getProductInfoByperformanceModsMpn(performanceModsMpn);
    }

    @Override
    public List<String> searchWarehouseMpn(String term) {
        return this.productDao.searchWarehouseMpn(term);
    }

    @Override
    public List<String> searchProductMpn(String term) {
        return this.productDao.searchProductMpn(term);
    }

    @Override
    public String getperformanceModsMpnByManufacturerMpn(String manufacturerMpn, int manufacturerId) {
        return this.productDao.getperformanceModsMpnByManufacturerMpn(manufacturerMpn, manufacturerId);
    }

    @Override
    public List<Product> getProductListForExcel(int productStatusId, int manufacturerId, String performanceModsMpn, String productName, String warehouseMpn, String productMpn) {
        return this.productDao.getProductListForExcel(productStatusId, manufacturerId, performanceModsMpn, productName, warehouseMpn, productMpn);
    }

    @Override
    public List<MarketplaceListingSkuDTO> getMarketplaceListingsAndSku(String performanceModsMpn) {
        return this.productDao.getMarketplaceListingsAndSku(performanceModsMpn);
    }

    @Override
    public List<Order> getLowCountProductForDashbored(int count) {
        return this.productDao.getLowCountProductForDashbored(count);
    }

    @Override
    public List<Product> getMissingProductDataList(int pageNo) {
        return this.productDao.getMissingProductDataList(pageNo);
    }

    @Override
    public int getMissingProductDataListCount() {
        return this.productDao.getMissingProductDataListCount();
    }

    @Override
    public List<Product> getProductListForDelet(String performanceModsMpn) {
        return this.productDao.getProductListForDelet(performanceModsMpn);
    }

    @Override
    public int deleteProductByProductID(int productId) {
        return this.productDao.deleteProductByProductID(productId);
    }

    @Override
    public List<ChildOfSubCategory> getListOfChildCategory() {
        return this.productDao.getListOfChildCategory();
    }

    @Override
    public List<ChildOfSubCategory> getChildCategoryListForSubCategory(int subCategoryId) {
        return this.productDao.getChildCategoryListForSubCategory(subCategoryId);
    }

    @Override
    public List<ChildOfChildCategory> getsubChildCategoryListForSubCategory(int childCategoryId) {
        return this.productDao.getsubChildCategoryListForSubCategory(childCategoryId);
    }

    @Override
    public int addChildCategory(String childCategoryDesc, boolean active) {
        try {
            return this.productDao.addChildCategory(childCategoryDesc, active);
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public List<Integer> getChildCategoryIdListBySubCategory(int subCategoryId) {
        List<Integer> subCategoryList = this.productDao.getChildCategoryIdListBySubCategory(subCategoryId);
        return subCategoryList;
    }

    @Override
    public List<Product> getDownloadProductList(int productStatusId, int manufacturerId, String productName, int pageNo, int mainCategoryId, int subCategoryId, int childCategoryId) {
        return this.productDao.getDownloadProductList(productStatusId, manufacturerId, productName, pageNo, mainCategoryId, subCategoryId, childCategoryId);
    }

    @Override
    public int getCountDownloadProductList(int productStatusId, int manufacturerId, String productName, int pageNo, int mainCategoryId, int subCategoryId, int childCategoryId) {
        return this.productDao.getCountDownloadProductList(productStatusId, manufacturerId, productName, pageNo, mainCategoryId, subCategoryId, childCategoryId);
    }

    @Override
    public List<SubCategory> getAllSubCategoryList() {
        return this.productDao.getAllSubCategoryList();
    }

    @Override
    public List<ChildOfSubCategory> getAllChildCategoryList() {
        return this.productDao.getAllChildCategoryList();
    }

    @Override
    public List<ChildOfChildCategory> getListOfSubChildCategory() {
        return this.productDao.getListOfSubChildCategory();
    }

    @Override
    public List<Integer> getSubChildCategoryIdListByChildCategory(int childCategoryId) {
        return this.productDao.getSubChildCategoryIdListByChildCategory(childCategoryId);
    }

    @Override
    public int addSubChildCategory(String subChildCategoryDesc, boolean active) {
        try {
            return this.productDao.addSubChildCategory(subChildCategoryDesc, active);
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public int saveMultipartFileDataForWebsiteProduct(UploadFeed uploadFeed, int companyId) {
        if (!uploadFeed.getMultipartFile().isEmpty()) {
            String originaFileName = uploadFeed.getMultipartFile().getOriginalFilename();
            Date curDate = DateUtils.getCurrentDateObject(DateUtils.IST);
            byte[] imgBytes = null;
            try {
                imgBytes = uploadFeed.getMultipartFile().getBytes();
            } catch (IOException ex) {
                Logger.getLogger(WarehouseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String path = WEBSITE_PRODUCT + sdf.format(curDate) + "/" + originaFileName;
            File folderFile = new File(WEBSITE_PRODUCT + sdf.format(curDate));
            try {
                folderFile.mkdirs();
            } catch (Exception e) {
                Logger.getLogger(ListingServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            }
            File someFile = new File(path);
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(someFile);
                fos.write(imgBytes);
                fos.flush();
                fos.close();
                LogUtils.systemLogger.info(LogUtils.buildStringForLog("File transferred.. :" + originaFileName, GlobalConstants.TAG_SYSTEMLOG));
                this.productDao.loadWebsiteProductDataLocally(path, companyId);
                return 1;
            } catch (FileNotFoundException ex) {
                LogUtils.systemLogger.warn(LogUtils.buildStringForLog("FileNotFoundException :" + ex, GlobalConstants.TAG_SYSTEMLOG));
                return 0;
            } catch (Exception e) {
                LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int UploadImageServiceImpl(UploadFeed uploadFeed) {
        if (!uploadFeed.getMultipartFile().isEmpty()) {

            String msg = this.productDao.uploadImageToS3Bucket(uploadFeed);
            return 1;
        } else {
            return 0;
        }

    }

    @Override
    public List<Company> getCompanyList(boolean active) {
        return this.productDao.getCompanyList(active);
    }

    
}
