/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.ChildCategory;
import com.aspirant.performanceModsAdminTool.model.ChildOfSubCategory;
import com.aspirant.performanceModsAdminTool.model.MainCategory;
import com.aspirant.performanceModsAdminTool.model.Manufacturer;
import com.aspirant.performanceModsAdminTool.model.Product;
import com.aspirant.performanceModsAdminTool.model.SubCategory;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Pallavi
 */
/*
Product Title;
 Brand	;
Model NO;
 Weight;
 Category;	
Sub-Category;	
Child Category;	
Short Description;
 LONG Description;	
Small Image Url;	
Large Image URL;
other image 1;
other image 2;
other image 3;

*/
public class ProductDownloadListRowMapper implements RowMapper<Product>{

    @Override
    public Product mapRow(ResultSet rs, int i) throws SQLException {
        Product product = new Product();
        product.setProductName(rs.getString("PRODUCT_NAME"));
        
        Manufacturer m = new Manufacturer();
        m.setManufacturerId(rs.getInt("MANUFACTURER_ID"));
        m.setManufacturerName(rs.getString("MANUFACTURER_NAME"));
        product.setManufacturer(m);
        
        product.setManufacturerMpn(rs.getString("MANUFACTURER_MPN"));
        product.setProductWeight(rs.getDouble("WEIGHT"));
        
        MainCategory mc = new MainCategory();
        mc.setMainCategoryId(rs.getInt("MAIN_CATEGORY_ID"));
        mc.setMainCategoryDesc(rs.getString("MAIN_CATEGORY_DESC"));
        product.setMainCategory(mc);
        
        SubCategory sc = new SubCategory();
        sc.setSubCategoryId(rs.getInt("SUB_CATEGORY_ID"));
        sc.setSubCategoryDesc(rs.getString("SUB_CATEGORY_DESC"));
        product.setSubCategory1(sc);
        
        ChildOfSubCategory cc = new ChildOfSubCategory();
        cc.setChildCategoryId(rs.getInt("CHILD_CATEGORY_ID"));
        cc.setChildCategoryDesc(rs.getString("CHILD_CATEGORY_DESC"));
        product.setChildCategory(cc);
        
        product.setShortDesc(rs.getString("SHORT_DESC"));
        product.setLongDesc(rs.getString("LONG_DESC"));
        
        product.setResizeImageUrl(rs.getString("RESIZE_IMAGE_URL"));
        
        product.setLargeImageUrl1(rs.getString("LARGE_IMAGE_URL"));
        
        return product;
    }
    
    
}
