/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.ChildOfChildCategory;
import com.aspirant.performanceModsAdminTool.model.ChildOfSubCategory;
import com.aspirant.performanceModsAdminTool.model.MainCategory;
import com.aspirant.performanceModsAdminTool.model.Manufacturer;
import com.aspirant.performanceModsAdminTool.model.Product;
import com.aspirant.performanceModsAdminTool.model.ProductStatus;
import com.aspirant.performanceModsAdminTool.model.SubCategory;
import com.aspirant.performanceModsAdminTool.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author shrutika
 */
public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int i) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getInt("PRODUCT_ID"));
        p.setProductName(rs.getString("PRODUCT_NAME"));

        Manufacturer mf = new Manufacturer();
        mf.setManufacturerId(rs.getInt("MANUFACTURER_ID"));
        mf.setManufacturerName(rs.getString("MANUFACTURER_NAME"));
        p.setManufacturer(mf);

        p.setManufacturerMpn(rs.getString("MANUFACTURER_MPN"));
        p.setProductTitle(rs.getString("TITLE"));
        p.setperformanceModsMpn(rs.getString("performanceMods_MPN"));
        p.setProductMap(rs.getDouble("MAP"));
        p.setProductMsrp(rs.getDouble("MSRP"));
        p.setProductWeight(rs.getDouble("WEIGHT"));
        p.setEstShippingWt(rs.getDouble("EST_SHIPPING_WT"));
        p.setProductLength(rs.getDouble("LENGTH"));
        p.setProductWidth(rs.getDouble("WIDTH"));
        p.setProductHeight(rs.getDouble("HEIGHT"));
        p.setUpc(rs.getString("UPC"));
        p.setAsin(rs.getString("ASIN"));
        p.setNeweggItemId(rs.getString("NEWEGG_ITEM_ID"));
        p.setNeweggB2BItemId(rs.getString("NEWEGG_BUSINESS_ITEM_ID"));

        MainCategory m = new MainCategory();
        m.setMainCategoryId(rs.getInt("MAIN_CATEGORY_ID"));
        m.setMainCategoryDesc(rs.getString("MAIN_CATEGORY_DESC"));
        p.setMainCategory(m);

        SubCategory s1 = new SubCategory();
        s1.setSubCategoryId(rs.getInt("SUB_CATEGORY1"));
        s1.setSubCategoryDesc(rs.getString("SUB_CATEGORY_DESC"));
        p.setSubCategory1(s1);

        ChildOfSubCategory s2 = new ChildOfSubCategory();
        s2.setChildCategoryId(rs.getInt("SUB_CATEGORY2"));
        s2.setChildCategoryDesc(rs.getString("CHILD_CATEGORY_DESC"));
        p.setChildCategory(s2);
        
        ChildOfChildCategory s3 = new ChildOfChildCategory();
        s3.setSubChildCategoryId(rs.getInt("SUB_CATEGORY3"));
        s3.setSubChildCategoryDesc(rs.getString("CHILD_SUB_CATEGORY_DESC"));
        p.setChildOfChildCategory(s3);

        p.setShortDesc(rs.getString("SHORT_DESC"));
        p.setLongDesc(rs.getString("LONG_DESC"));
        p.setReturnable(rs.getBoolean("RETURNABLE"));
        p.setKeywords(rs.getString("KEYWORDS"));
        p.setResizeImageUrl(rs.getString("RESIZE_IMAGE_URL"));
        p.setNotes(rs.getString("NOTES"));
        p.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
        p.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));

        User user1 = new User();
        user1.setUserId(rs.getInt("CREATED_BY"));
        user1.setUsername(rs.getString("createdBy"));
        p.setCreatedBy(user1);

        User user2 = new User();
        user2.setUserId(rs.getInt("LAST_MODIFIED_BY"));
        user2.setUsername(rs.getString("lastModifiedBy"));
        p.setLastModifiedBy(user2);

        p.setActive(rs.getBoolean("ACTIVE"));

        ProductStatus ps = new ProductStatus();
        ps.setProductStatusDesc(rs.getString("PRODUCT_STATUS_DESC"));
        p.setProductStatus(ps);

        p.setLargeImageUrl1(rs.getString("largeImageUrl1"));
        p.setLargeImageUrl2(rs.getString("largeImageUrl2"));
        p.setLargeImageUrl3(rs.getString("largeImageUrl3"));
        p.setLargeImageUrl4(rs.getString("largeImageUrl4"));

        p.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
        p.setWarehouseMpn(rs.getString("WAREHOUSE_MPN"));

        return p;
    }
}
