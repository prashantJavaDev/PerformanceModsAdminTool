/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.MarketplaceDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.Marketplace;
import com.aspirant.performanceModsAdminTool.model.rowmapper.MarketplaceRowMapper;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.utils.DateUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ritesh
 */
@Repository
public class MarketplaceDaoImpl implements MarketplaceDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int addMarketplace(Marketplace marketplace) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        SimpleJdbcInsert marketplaceInsert = new SimpleJdbcInsert(this.dataSource).withTableName("tesy_marketplace").usingGeneratedKeyColumns("MARKETPLACE_ID");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("MARKETPLACE_NAME", marketplace.getMarketplaceName());
        params.put("ACTIVE", marketplace.isActive());
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        int marketplaceId = marketplaceInsert.executeAndReturnKey(params).intValue();
        return marketplaceId;
    }

    @Override
    public List<Marketplace> getMarketplaceList(boolean active) {
        String sql = "SELECT tm.* FROM tesy_marketplace tm";
        if (active) {
            sql += " WHERE tm.ACTIVE";
        }
        sql += " ORDER BY tm.`LAST_MODIFIED_DATE` DESC";
        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new MarketplaceRowMapper());
    }

    @Override
    public Marketplace getMarketplaceByMarketplaceId(int marketplaceId) {
        String sqlString = "SELECT tm.* FROM tesy_marketplace tm"
                + " WHERE tm.`MARKETPLACE_ID`=?";
        Object params[] = new Object[]{marketplaceId};
        return this.jdbcTemplate.queryForObject(sqlString, params, new MarketplaceRowMapper());
    }

    @Override
    public int updateMarketplace(Marketplace marketplace) {
        String sqlString;
        Object params[];
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        sqlString = "UPDATE tesy_marketplace SET MARKETPLACE_NAME=?, ACTIVE=?, LAST_MODIFIED_BY=?, LAST_MODIFIED_DATE=? WHERE MARKETPLACE_ID=?";
        params = new Object[]{marketplace.getMarketplaceName(), marketplace.isActive(), curUser, curDate, marketplace.getMarketplaceId()};
        return this.jdbcTemplate.update(sqlString, params);
    }
    
    public void processOrderAutomatically(){
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        Object params[];
        String sql= "UPDATE tesy_order tso LEFT JOIN tesy_available_listing tal ON tso.`MARKETPLACE_SKU`=tal.`SKU`\n"
                + "SET tso.`WAREHOUSE_ID`=tal.`WAREHOUSE_ID`, tso.`PROCESSED_BY`=1, tso.`PROCESSED_DATE`=? WHERE tso.`MARKETPLACE_SKU`=tal.`SKU`";
        params= new Object[]{curDate};
        this.jdbcTemplate.update(sql,params);
        
    }
}
