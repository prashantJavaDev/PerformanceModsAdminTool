/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.ConfigDao;
import com.aspirant.performanceModsAdminTool.model.AmazonProperties;
import com.aspirant.performanceModsAdminTool.model.rowmapper.AmazonPropertiesRowMapper;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author pk
 */
@Repository
public class ConfigDaoImpl implements ConfigDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public AmazonProperties getAmazonProperties() {
        try {
            String sql = "SELECT * FROM amazon_properties  t WHERE t.`credential_id`='1';";
            AmazonProperties obj = this.jdbcTemplate.queryForObject(sql, new AmazonPropertiesRowMapper());
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
