/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.ChildOfSubCategory;
import com.aspirant.performanceModsAdminTool.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Pallavi
 */
public class ChildCategoryRowMapper implements RowMapper<ChildOfSubCategory>{

    @Override
    public ChildOfSubCategory mapRow(ResultSet rs, int i) throws SQLException {
        
        ChildOfSubCategory c = new ChildOfSubCategory();
        
        c.setChildCategoryId(rs.getInt("CHILD_CATEGORY_ID"));
        c.setChildCategoryDesc(rs.getString("CHILD_CATEGORY_DESC"));
        c.setActive(rs.getBoolean("ACTIVE"));
        
        c.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
        c.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));
        
        User user1 = new User();
        user1.setUserId(rs.getInt("CREATED_BY"));
        user1.setUsername(rs.getString("createdBy"));
        c.setCreatedBy(user1);
        
        User user2 = new User();
        user2.setUserId(rs.getInt("LAST_MODIFIED_BY"));
        user2.setUsername(rs.getString("lastModifiedBy"));
        c.setLastModifiedBy(user2);
        
        
        return c;
    }
    
    
}
