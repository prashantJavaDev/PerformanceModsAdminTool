/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.ProductStatus;
import com.aspirant.performanceModsAdminTool.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author shrutika
 */
public class ProductStatusRowMapper implements RowMapper<ProductStatus> {

    @Override
    public ProductStatus mapRow(ResultSet rs, int i) throws SQLException {
        ProductStatus p = new ProductStatus();
        p.setProductStatusId(rs.getInt("PRODUCT_STATUS_ID"));
        p.setProductStatusDesc(rs.getString("PRODUCT_STATUS_DESC"));
        p.setActive(rs.getBoolean("ACTIVE"));
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

        return p;
    }
}
