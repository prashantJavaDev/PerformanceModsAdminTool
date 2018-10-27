/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.SubCategory;
import com.aspirant.performanceModsAdminTool.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author shrutika
 */
public class SubCategoryRowMapper implements RowMapper<SubCategory> {

    @Override
    public SubCategory mapRow(ResultSet rs, int i) throws SQLException {
        SubCategory s = new SubCategory();
        s.setSubCategoryId(rs.getInt("SUB_CATEGORY_ID"));
        s.setSubCategoryDesc(rs.getString("SUB_CATEGORY_DESC"));
        s.setActive(rs.getBoolean("ACTIVE"));
        s.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
        s.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));

        User user1 = new User();
        user1.setUserId(rs.getInt("CREATED_BY"));
        user1.setUsername(rs.getString("createdBy"));
        s.setCreatedBy(user1);

        User user2 = new User();
        user2.setUserId(rs.getInt("LAST_MODIFIED_BY"));
        user2.setUsername(rs.getString("lastModifiedBy"));
        s.setLastModifiedBy(user2);
        
        return s;
    }
}
