/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.Manufacturer;
import com.aspirant.performanceModsAdminTool.model.Product;
import com.aspirant.performanceModsAdminTool.model.ProductStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Ritesh
 */
public class ProductListRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int i) throws SQLException {
        Product p = new Product();
        p.setProductId(rs.getInt("PRODUCT_ID"));
        p.setProductName(rs.getString("PRODUCT_NAME"));
        p.setperformanceModsMpn(rs.getString("ADMIN_TOOL_MPN"));

        Manufacturer mf = new Manufacturer();
        mf.setManufacturerId(rs.getInt("MANUFACTURER_ID"));
        mf.setManufacturerName(rs.getString("MANUFACTURER_NAME"));
        p.setManufacturer(mf);

        p.setManufacturerMpn(rs.getString("MANUFACTURER_MPN"));
        p.setProductMap(rs.getDouble("MAP"));
        p.setProductWeight(rs.getDouble("WEIGHT"));
        p.setUpc(rs.getString("UPC"));

        ProductStatus ps = new ProductStatus();
        ps.setProductStatusDesc(rs.getString("PRODUCT_STATUS_DESC"));
        p.setProductStatus(ps);

        p.setWarehouseName(rs.getString("WAREHOUSE_NAME"));
        p.setWarehouseMpn(rs.getString("WAREHOUSE_MPN"));

        return p;
    }
}
