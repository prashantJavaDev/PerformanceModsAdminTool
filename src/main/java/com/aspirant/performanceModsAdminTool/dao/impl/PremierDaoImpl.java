/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.PremierDao;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pk
 */
@Repository
public class PremierDaoImpl implements PremierDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<String> getFeedEntries() {
        try {
           List<String> list= new ArrayList<>();
           list.add("BUB176757");
           list.add("SKYB8507");
           list.add("SKYM9592");
           return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updateTokenOfPremier(String sessionToken) {
        try {
            String sql = "UPDATE token t SET t.`ACCESS_TOKEN`=? WHERE t.`TOKEN_API`='P';";
            return this.jdbcTemplate.update(sql, sessionToken);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
