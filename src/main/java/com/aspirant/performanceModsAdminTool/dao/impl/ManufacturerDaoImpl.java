/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.ManufacturerDao;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.Manufacturer;
import com.aspirant.performanceModsAdminTool.model.rowmapper.ManufacturerRowMapper;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ritesh
 */
@Repository
public class ManufacturerDaoImpl implements ManufacturerDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public int addManufacturer(Manufacturer manufacturer) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        SimpleJdbcInsert manufacturerInsert = new SimpleJdbcInsert(this.dataSource).withTableName("tesy_manufacturer").usingGeneratedKeyColumns("MANUFACTURER_ID");
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put("MANUFACTURER_NAME", manufacturer.getManufacturerName());
        params.put("MANUFACTURER_CODE", manufacturer.getManufacturerCode());
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("ACTIVE", 1);
        int manufacturerId = manufacturerInsert.executeAndReturnKey(params).intValue();

        SimpleJdbcInsert mapManufacturerInsert = new SimpleJdbcInsert(this.dataSource).withTableName("tesy_manufacturer_mapping");
        params.put("MANUFACTURER_ID", manufacturerId);
        params.put("WH_MANUFACTURER_NAME", manufacturer.getManufacturerName());
        mapManufacturerInsert.execute(params);
        return manufacturerId;
    }

    @Override
    public void mapManufacturer(Manufacturer manufacturer) {
        SimpleJdbcInsert manufacturerInsert = new SimpleJdbcInsert(this.dataSource).withTableName("tesy_manufacturer_mapping");
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put("MANUFACTURER_ID", manufacturer.getManufacturerId());
        params.put("WH_MANUFACTURER_NAME", manufacturer.getManufacturerName());
        manufacturerInsert.execute(params);
    }

    @Override
    public List<String> GetListofMatchingManufacturerCode(String manufacturerCode) {
        String sql = " SELECT c.`MANUFACTURER_CODE` FROM tesy_manufacturer c WHERE c.`MANUFACTURER_CODE`= '" + manufacturerCode + "';";
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        return list;
    }

    @Override
    public List<Manufacturer> mapManufacturerList(int manufacturerId) {
        String sql = "SELECT tmm.`MANUFACTURER_ID`,tm.`MANUFACTURER_CODE`,tmm.`WH_MANUFACTURER_NAME` MANUFACTURER_NAME FROM tesy_manufacturer_mapping tmm"
                + " LEFT JOIN tesy_manufacturer tm ON tm.`MANUFACTURER_ID`=tmm.`MANUFACTURER_ID`"
                + " WHERE tmm.`MANUFACTURER_ID`=? ORDER BY tmm.`WH_MANUFACTURER_NAME`";
        return this.jdbcTemplate.query(sql, new ManufacturerRowMapper(), manufacturerId);
    }
}
