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
        SimpleJdbcInsert manufacturerInsert = new SimpleJdbcInsert(this.dataSource).withTableName("pm_manufacturer").usingGeneratedKeyColumns("MANUFACTURER_ID");
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put("MANUFACTURER_NAME", manufacturer.getManufacturerName());
        params.put("MANUFACTURER_CODE", manufacturer.getManufacturerCode());
        params.put("CREATED_BY", curUser);
        params.put("CREATED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("ACTIVE", 1);
        int manufacturerId = manufacturerInsert.executeAndReturnKey(params).intValue();

        SimpleJdbcInsert mapManufacturerInsert = new SimpleJdbcInsert(this.dataSource).withTableName("pm_manufacturer_mapping");
        params.put("MANUFACTURER_ID", manufacturerId);
        params.put("WH_MANUFACTURER_NAME", manufacturer.getManufacturerName());
        mapManufacturerInsert.execute(params);
        return manufacturerId;
    }

    @Override
    public void mapManufacturer(Manufacturer manufacturer) {
        SimpleJdbcInsert manufacturerInsert = new SimpleJdbcInsert(this.dataSource).withTableName("pm_manufacturer_mapping");
        java.util.Map<String, Object> params = new HashMap<String, Object>();
        params.put("MANUFACTURER_ID", manufacturer.getManufacturerId());
        params.put("WH_MANUFACTURER_NAME", manufacturer.getManufacturerName());
        manufacturerInsert.execute(params);
    }

    @Override
    public List<String> GetListofMatchingManufacturerCode(String manufacturerCode) {
        String sql = " SELECT c.`MANUFACTURER_CODE` FROM pm_manufacturer c WHERE c.`MANUFACTURER_CODE`= '" + manufacturerCode + "';";
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        return list;
    }

    @Override
    public List<Manufacturer> mapManufacturerList(int manufacturerId) {
        String sql = "SELECT tmm.`MANUFACTURER_ID`,tm.`MANUFACTURER_CODE`,tmm.`WH_MANUFACTURER_NAME` MANUFACTURER_NAME FROM pm_manufacturer_mapping tmm"
                + " LEFT JOIN pm_manufacturer tm ON tm.`MANUFACTURER_ID`=tmm.`MANUFACTURER_ID`"
                + " WHERE tmm.`MANUFACTURER_ID`=? ORDER BY tmm.`WH_MANUFACTURER_NAME`";
        return this.jdbcTemplate.query(sql, new ManufacturerRowMapper(), manufacturerId);
    }

    @Override
    @Transactional
    public int deleteManufacturer(int manufacturerId) {
        try {
            String sql1 = "DELETE FROM pm_current_warehouse_product WHERE PRODUCT_ID IN(SELECT tp.`PRODUCT_ID` FROM pm_product tp WHERE MANUFACTURER_ID=?);";
            this.jdbcTemplate.update(sql1, manufacturerId);

            String sql2 = "DELETE FROM pm_warehouse_product_mpn WHERE PRODUCT_ID IN(SELECT tp.`PRODUCT_ID` FROM pm_product tp WHERE MANUFACTURER_ID=?)";
            this.jdbcTemplate.update(sql2, manufacturerId);

            String sql3 = "DELETE FROM pm_warehouse_feed_data WHERE PRODUCT_ID IN(SELECT tp.`PRODUCT_ID` FROM pm_product tp WHERE MANUFACTURER_ID=?)";
            this.jdbcTemplate.update(sql3, manufacturerId);

            String sql4 = "DELETE FROM pm_product_image WHERE PRODUCT_ID IN(SELECT tp.`PRODUCT_ID` FROM pm_product tp WHERE MANUFACTURER_ID=?)";
            this.jdbcTemplate.update(sql4, manufacturerId);

            String sql5 = "DELETE FROM pm_product WHERE MANUFACTURER_ID=?";
            this.jdbcTemplate.update(sql5, manufacturerId);
            
            String sql6 = "DELETE FROM pm_manufacturer_mapping WHERE MANUFACTURER_ID=?";
            this.jdbcTemplate.update(sql6, manufacturerId);
            
            String sql7 = "DELETE FROM pm_mpn_sku_mapping WHERE MANUFACTURER_ID=?";
            this.jdbcTemplate.update(sql7, manufacturerId);
            
            String sql8 = "DELETE FROM pm_manufacturer WHERE MANUFACTURER_ID=?";
            this.jdbcTemplate.update(sql8, manufacturerId);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
