/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.model.rowmapper;

import com.aspirant.performanceModsAdminTool.model.MainCategory;
import com.aspirant.performanceModsAdminTool.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author shrutika
 */
public class MainCategoryRowMapper implements RowMapper<MainCategory> {

    @Override
    public MainCategory mapRow(ResultSet rs, int i) throws SQLException {
        MainCategory m = new MainCategory();
        m.setMainCategoryId(rs.getInt("MAIN_CATEGORY_ID"));
        m.setMainCategoryDesc(rs.getString("MAIN_CATEGORY_DESC"));
        m.setActive(rs.getBoolean("ACTIVE"));
        m.setCreatedDate(rs.getTimestamp("CREATED_DATE"));
        m.setLastModifiedDate(rs.getTimestamp("LAST_MODIFIED_DATE"));

        User user1 = new User();
        user1.setUserId(rs.getInt("CREATED_BY"));
        user1.setUsername(rs.getString("createdBy"));
        m.setCreatedBy(user1);

        User user2 = new User();
        user2.setUserId(rs.getInt("LAST_MODIFIED_BY"));
        user2.setUsername(rs.getString("lastModifiedBy"));
        m.setLastModifiedBy(user2);

        return m;
    }
}
