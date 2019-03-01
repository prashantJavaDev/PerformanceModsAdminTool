/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.ListingDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.DTO.ProductListingDTO;
import com.aspirant.performanceModsAdminTool.model.DTO.mapper.ProductListingDTORowMapper;
import com.aspirant.performanceModsAdminTool.model.Listing;
import com.aspirant.performanceModsAdminTool.model.Marketplace;
import com.aspirant.performanceModsAdminTool.model.rowmapper.ExportMarketPlaceFeesListingRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.MarketplaceOrderDetailsRowMapper;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Ritesh
 */
@Repository
public class ListingDaoImpl implements ListingDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public void loadFeesDataLocally(String path, int marketplaceId) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        String sql = "TRUNCATE TABLE `performance_mods`.`pm_temp_fees`";
        this.jdbcTemplate.update(sql);
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Truncate temp_table done.", GlobalConstants.TAG_SYSTEMLOG));

        sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `performance_mods`.`pm_temp_fees` CHARACTER SET 'latin1' FIELDS ESCAPED BY '\"' TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 LINES (`MARKETPLACE_LISTING_ID`, `FEES`); ";
        this.jdbcTemplate.execute(sql);
        // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Load data done..", GlobalConstants.TAG_SYSTEMLOG));

        sql = "UPDATE pm_available_listing tal\n"
                + " LEFT JOIN pm_temp_fees ttf ON tal.`MARKETPLACE_LISTING_ID`=ttf.`MARKETPLACE_LISTING_ID`\n"
                + " SET tal.`CURRENT_COMMISSION_PERCENTAGE`=COALESCE(ttf.`FEES`,'15'),tal.`LAST_MODIFIED_BY`=?,tal.`LAST_MODIFIED_DATE`=?\n"
                + " WHERE tal.`MARKETPLACE_ID`=?";
        jdbcTemplate.update(sql, curUser, curDate, marketplaceId);
    }

    @Override
    public List<ProductListingDTO> getListingList(int marketplaceId, int warehouseId, String marketplaceSku, int pageNo, boolean active, int[] manufacturerId) {

        StringBuilder manufacturerIdsStr = new StringBuilder();
        if (manufacturerId != null) {
            for (int sId : manufacturerId) {
                manufacturerIdsStr.append(sId).append(",");
            }
            if (manufacturerIdsStr.length() > 0) {
                if (manufacturerIdsStr.charAt(manufacturerIdsStr.length() - 1) == ',') {
                    manufacturerIdsStr.setLength(manufacturerIdsStr.length() - 1);
                }
            }
        }

        String sql = "SELECT tal.`ACTIVE`,tal.`MARKETPLACE_LISTING_ID`,tal.`SKU`,\n"
                + " tal.`LAST_LISTED_PRICE` ,tal.`LAST_LISTED_QUANTITY`,tal.`LAST_LISTED_DATE`,\n"
                + " tal.`CURRENT_PRICE`, tal.`CURRENT_QUANTITY`,tal.`CURRENT_LISTED_DATE`, \n"
                + " tw.`WAREHOUSE_NAME`,tal.`WAREHOUSE_ID`,tmsm.`MANUFACTURER_MPN` ,\n"
                + " tm.`MANUFACTURER_NAME`,tal.`CURRENT_SHIPPING`,tal.`CURRENT_COMMISSION`,\n"
                + " tal.`CURRENT_PROFIT`,tal.`CURRENT_PROFIT_PERCENTAGE`,tmsm.`PACK`,\n"
                + " tal.`CURRENT_COMMISSION_PERCENTAGE`,tcwp.`PRICE`,tcwp.`QUANTITY`\n"
                + " FROM pm_available_listing tal \n"
                + " LEFT JOIN pm_warehouse tw ON tw.`WAREHOUSE_ID`=tal.`WAREHOUSE_ID` \n"
                + " LEFT JOIN pm_mpn_sku_mapping tmsm ON tmsm.`SKU`=tal.`SKU`\n"
                + " LEFT JOIN pm_manufacturer tm ON tm.`MANUFACTURER_ID`=tmsm.`MANUFACTURER_ID`\n"
                + " LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tmsm.`MANUFACTURER_MPN` \n"
                + " AND tp.`MANUFACTURER_ID`=tmsm.`MANUFACTURER_ID`\n"
                + " LEFT JOIN pm_current_warehouse_product tcwp ON tcwp.`PRODUCT_ID`=tp.`PRODUCT_ID`\n"
                + " AND tcwp.`WAREHOUSE_ID`=tal.`WAREHOUSE_ID`\n"
                + " WHERE tal.`MARKETPLACE_ID`=:marketplaceId";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("marketplaceId", marketplaceId);

        if (warehouseId != 0) {
            sql += " AND tal.WAREHOUSE_ID=:warehouseId ";
            params.put("warehouseId", warehouseId);
        }

        if (marketplaceSku != null && marketplaceSku != "") {
            sql += " AND tal.SKU=:marketplaceSku ";
            params.put("marketplaceSku", marketplaceSku);
        }

        if (manufacturerIdsStr.length() > 0) {
            sql += " AND tm.MANUFACTURER_ID IN (" + manufacturerIdsStr.toString() + ") ";
        }

        if (active) {
            sql += " AND tal.ACTIVE ";
        }

        sql += " ORDER BY tal.`SKU` ";

        if (pageNo != -1) {
            sql += " LIMIT " + (pageNo - 1) * GlobalConstants.PAGE_SIZE + "," + GlobalConstants.PAGE_SIZE;
        }

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        return nm.query(sql, params, new ProductListingDTORowMapper());
    }

    @Override
    public int getListingCount(int marketplaceId, int warehouseId, String marketplaceSku, boolean active, int[] manufacturerId) {
        StringBuilder manufacturerIdsStr = new StringBuilder();
        if (manufacturerId != null) {
            for (int sId : manufacturerId) {
                manufacturerIdsStr.append(sId).append(",");
            }
            if (manufacturerIdsStr.length() > 0) {
                if (manufacturerIdsStr.charAt(manufacturerIdsStr.length() - 1) == ',') {
                    manufacturerIdsStr.setLength(manufacturerIdsStr.length() - 1);
                }
            }
        }
        String sql = "SELECT COUNT(*) FROM pm_available_listing tal\n"
                + " LEFT JOIN pm_mpn_sku_mapping tmsm ON tmsm.`SKU`=tal.`SKU`\n"
                + " LEFT JOIN pm_manufacturer tm ON tm.`MANUFACTURER_ID`=tmsm.`MANUFACTURER_ID`\n"
                + " WHERE tal.`MARKETPLACE_ID`=:marketplaceId";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("marketplaceId", marketplaceId);

        if (warehouseId != 0) {
            sql += " AND tal.WAREHOUSE_ID=:warehouseId ";
            params.put("warehouseId", warehouseId);
        }

        if (marketplaceSku != null && marketplaceSku != "") {
            sql += " AND tal.SKU=:marketplaceSku ";
            params.put("marketplaceSku", marketplaceSku);
        }

        if (manufacturerIdsStr.length() > 0) {
            sql += " AND tm.MANUFACTURER_ID IN (" + manufacturerIdsStr.toString() + ") ";
        }

        if (active) {
            sql += " AND tal.ACTIVE ";
        }

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        Integer i = nm.queryForObject(sql, params, Integer.class);
        if (i == null) {
            return 0;
        } else {
            return i.intValue();
        }
    }

    @Override
    public int updatePriceQuantity(String sku, double price, int quantity, double profit, boolean active) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);

        String sql1 = "SELECT  tal.`CURRENT_PRICE` FROM pm_available_listing tal WHERE tal.`SKU`=?";
        float cost = this.jdbcTemplate.queryForObject(sql1, Float.class, sku);
        if (price > cost) {
            profit += price - cost;
        } else {
            profit -= cost - price;
        }
        String sql = "UPDATE pm_available_listing tal SET tal.`CURRENT_PRICE`=?,tal.`CURRENT_PROFIT`=?, tal.`CURRENT_QUANTITY`=?,tal.`ACTIVE`=?,tal.`LAST_MODIFIED_DATE`=?,tal.`LAST_MODIFIED_BY`=? WHERE tal.`SKU`=?";
        return this.jdbcTemplate.update(sql, price, profit, quantity, active, curDate, curUser, sku);
    }

    @Override
    @Transactional
    public void UpdateListings(int marketplaceId) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        try {
            String sqlupt="UPDATE pm_available_listing tal SET tal.`CURRENT_QUANTITY`=0 WHERE tal.`CURRENT_QUANTITY` IS NULL";
            this.jdbcTemplate.update(sqlupt);
            
            String sql = "UPDATE pm_available_listing tal SET tal.`LAST_LISTED_PRICE`=tal.`CURRENT_PRICE`, tal.`LAST_LISTED_QUANTITY`=tal.`CURRENT_QUANTITY`,tal.`LAST_LISTED_DATE`=NOW(),"
                    + " tal.`CURRENT_LISTED_DATE`=NOW(),tal.`LAST_MODIFIED_DATE`=?,tal.`LAST_MODIFIED_BY`=? WHERE"
                    + " tal.ACTIVE AND tal.`MARKETPLACE_ID`=?";
            this.jdbcTemplate.update(sql, curDate, curUser, marketplaceId);

            String sqlInsert = "INSERT INTO export_listing_data_trans \n"
                    + " SELECT NULL,tal.`MARKETPLACE_ID`,tal.`MARKETPLACE_LISTING_ID`,tal.`SKU`,"
                    + " tal.`CURRENT_PRICE`,tal.`CURRENT_QUANTITY`,tal.`CURRENT_PROFIT_PERCENTAGE`,"
                    + " tal.`CURRENT_PROFIT`,tal.`CURRENT_COMMISSION`,tal.`CURRENT_COMMISSION_PERCENTAGE`,tal.`CURRENT_SHIPPING`,"
                    + " tal.`WAREHOUSE_ID`,NOW() FROM pm_available_listing tal\n"
                    + "WHERE tal.`ACTIVE` AND tal.`MARKETPLACE_ID`=?";
            this.jdbcTemplate.update(sqlInsert, marketplaceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> getProfitPercentageList() {
        String sql = "SELECT PROFIT_PERCENTAGE FROM pm_profit_percentage ORDER BY PROFIT_ID";
        return this.jdbcTemplate.queryForList(sql, Integer.class);
    }

    @Override
    public List<String> searchSku(String term) {
        String sql = "SELECT t.`SKU` FROM pm_mpn_sku_mapping  t WHERE t.`SKU` LIKE '%" + term + "%'";

        // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG)O);
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        return list;
    }

    @Override
    @Transactional
    public int processListing(int marketplaceId, int manufacturerId) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);

        String sql = "UPDATE pm_available_listing tal"
                + "  LEFT JOIN pm_mpn_sku_mapping tmsm ON tmsm.`SKU`=tal.`SKU`"
                + "  LEFT JOIN pm_product tp ON tp.`MANUFACTURER_MPN`=tmsm.`MANUFACTURER_MPN` AND tp.`MANUFACTURER_ID`=tmsm.`MANUFACTURER_ID`"
                + "  LEFT JOIN (SELECT t.`CALCULATED_PRICE` PRICE, t.`PRODUCT_ID`,t.`WAREHOUSE_ID`,t.`QUANTITY`,t.`SHIPPING`"
                + "  FROM pm_current_warehouse_product t WHERE t.`QUANTITY`>0 AND t.`CALCULATED_PRICE`IN ("
                + "  SELECT MIN(t.`CALCULATED_PRICE`) FROM pm_current_warehouse_product t GROUP BY t.`PRODUCT_ID`  "
                + "  ) GROUP BY t.`PRODUCT_ID`) tc ON tc.PRODUCT_ID=tp.`PRODUCT_ID`"
                + "  LEFT JOIN (SELECT t.`CALCULATED_PRICE` PRICE,t.`PRODUCT_ID`,t.`WAREHOUSE_ID`,t.`QUANTITY`,t.`SHIPPING`"
                + "  FROM pm_current_warehouse_product t WHERE t.`QUANTITY`<=1   AND t.`CALCULATED_PRICE`IN ("
                + "  SELECT MIN(t.`CALCULATED_PRICE`) FROM pm_current_warehouse_product t GROUP BY t.`PRODUCT_ID`"
                + "  ) GROUP BY t.`PRODUCT_ID`) tc1 ON tc1.PRODUCT_ID=tp.`PRODUCT_ID`"
                + "  SET tal.`CURRENT_PRICE`=IF(tal.`ISMAP` AND"
                + " (COALESCE(ROUND((((COALESCE(tc.`PRICE`,tc1.`PRICE`))*tal.`CURRENT_COMMISSION_PERCENTAGE`)/100)+((COALESCE(tc.`PRICE`,tc1.`PRICE`))*tmsm.`PACK`),2),0))<tp.`MAP`,tp.`MAP`,"
                + " COALESCE(ROUND(((((COALESCE(tc.`PRICE`,tc1.`PRICE`)))*tal.`CURRENT_COMMISSION_PERCENTAGE`)/100)+((COALESCE(tc.`PRICE`,tc1.`PRICE`))*tmsm.`PACK`),2),0)),"
                + "  tal.`WAREHOUSE_ID`=COALESCE(tc.`WAREHOUSE_ID`,tc1.WAREHOUSE_ID),"
                + "  tal.`CURRENT_QUANTITY`=(tc.`QUANTITY`/tal.`PACK`),"
                + "  tal.`CURRENT_LISTED_DATE`=:curDate, "
                + "  tal.`CURRENT_SHIPPING`=COALESCE(tc.`SHIPPING`,tc1.`SHIPPING`),tal.`LAST_MODIFIED_DATE`=:curDate,tal.`LAST_MODIFIED_BY`=:curUser,"
                + "  tal.`CURRENT_COMMISSION`=((((COALESCE(tc.`PRICE`,tc1.`PRICE`))*tmsm.`PACK`)*tal.`CURRENT_COMMISSION_PERCENTAGE`)/100)"
                + "  WHERE tal.`MARKETPLACE_ID`=:marketplaceId  AND tp.`ACTIVE` AND tal.`ACTIVE`";

        String sql1 = "UPDATE pm_available_listing tal"
                + " SET tal.`CURRENT_PRICE`=tal.`CURRENT_PRICE`+("
                + " IF (COALESCE(tal.`CURRENT_PRICE`)>=300 AND COALESCE(tal.`CURRENT_PRICE`)<=600,3.5,"
                + " IF (COALESCE(tal.`CURRENT_PRICE`)>=600 AND COALESCE(tal.`CURRENT_PRICE`)<=1000,7.5,"
                + " IF (COALESCE(tal.`CURRENT_PRICE`)>=1000 AND COALESCE(tal.`CURRENT_PRICE`)<=1500,12.5,"
                + " IF (COALESCE(tal.`CURRENT_PRICE`)>=1500 AND COALESCE(tal.`CURRENT_PRICE`)<=2000,20,"
                + " IF (COALESCE(tal.`CURRENT_PRICE`)>=2000 AND COALESCE(tal.`CURRENT_PRICE`)<=4000,100,"
                + " IF (COALESCE(tal.`CURRENT_PRICE`)>=4000 AND COALESCE(tal.`CURRENT_PRICE`)<=5000,150,"
                + " IF (COALESCE(tal.`CURRENT_PRICE`)>=5000,200,1)))))))),"
                + " tal.`CURRENT_PROFIT`=("
                + " IF (COALESCE(tal.`CURRENT_PRICE`)>=300 AND COALESCE(tal.`CURRENT_PRICE`)<=600,3.5,"
                + " IF (COALESCE(tal.`CURRENT_PRICE`)>=600 AND COALESCE(tal.`CURRENT_PRICE`)<=1000,7.5,"
                + " IF (COALESCE(tal.`CURRENT_PRICE`)>=1000 AND COALESCE(tal.`CURRENT_PRICE`)<=1500,12.5,"
                + " IF (COALESCE(tal.`CURRENT_PRICE`)>=1500 AND COALESCE(tal.`CURRENT_PRICE`)<=2000,20,"
                + " IF (COALESCE(tal.`CURRENT_PRICE`)>=2000 AND COALESCE(tal.`CURRENT_PRICE`)<=4000,100,"
                + " IF (COALESCE(tal.`CURRENT_PRICE`)>=4000 AND COALESCE(tal.`CURRENT_PRICE`)<=5000,150,"
                + " IF (COALESCE(tal.`CURRENT_PRICE`)>=5000,200,1))))))))"
                + " WHERE tal.`CURRENT_PRICE`>0";

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> params1 = new HashMap<String, Object>();
        //params.put("profitPercentage", profitPercentage);
        params.put("curDate", curDate);
        params.put("curUser", curUser);
        params.put("marketplaceId", marketplaceId);

        if (manufacturerId != 0) {
            sql += " AND tmsm.MANUFACTURER_ID=:manufacturerId ";
            params.put("manufacturerId", manufacturerId);
        }
        int update = nm.update(sql, params);
        if (update > 0) {
            nm.update(sql1, params1);
        }
        return update;
    }

    @Override
    public void flushFeesStatus() {
        String sql = "UPDATE pm_available_listing tal SET tal.`FEED_STATUS`=0";
        this.jdbcTemplate.update(sql);
    }

    @Override
    public List<Listing> exportMarketplaceFees(int marketplaceId, int pageNo) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT tal.`MARKETPLACE_LISTING_ID`,tal.`SKU`,tal.`CURRENT_PRICE`,tal.`CURRENT_COMMISSION` FROM pm_available_listing tal WHERE tal.`MARKETPLACE_ID`=:marketplaceId");
//            if (pageNo != -1) {
//                sql.append(" LIMIT ").append((pageNo - 1) * GlobalConstants.PAGE_SIZE).append(",").append(GlobalConstants.PAGE_SIZE);
//            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("marketplaceId", marketplaceId);
            NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

            return nm.query(sql.toString(), params, new ExportMarketPlaceFeesListingRowMapper());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getExportMarketplaceFeesCount(int marketplaceId) {

        String sql = "SELECT COUNT(*) FROM pm_available_listing tal WHERE tal.`MARKETPLACE_ID`=?";
        Object[] param = {marketplaceId};
        return this.jdbcTemplate.queryForInt(sql, param);
    }

    @Override
    public void loadFeesDataLocally1(String path, int marketplaceId) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        try {
            String sql = "TRUNCATE TABLE `performance_mods`.`pm_temp_available_listing`";
            String sql1;
            this.jdbcTemplate.update(sql);
            // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Truncate pm_temp_available_listing done.", GlobalConstants.TAG_SYSTEMLOG));

            sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `performance_mods`.`pm_temp_available_listing` CHARACTER SET 'latin1' FIELDS ESCAPED BY '\"' TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 LINES (`MARKETPLACE_LISTING_ID`, `SKU`, `LAST_LISTED_PRICE`, `LAST_LISTED_QUANTITY`,`PACK`,`MANUFACTURER_NAME`,`MPN`); ";
            this.jdbcTemplate.execute(sql);
            // LogUtils.systemLogger.info(LogUtils.buildStringForLog("Load data done..", GlobalConstants.TAG_SYSTEMLOG));
            
            sql = "UPDATE pm_temp_available_listing ttp SET ttp.`MPN`=REPLACE(REPLACE(ttp.`MPN`, '\\r', ''), '\\n', '')";
            this.jdbcTemplate.update(sql);

//            sql = "UPDATE pm_available_listing tal\n"
//                    + " LEFT JOIN pm_temp_available_listing ttf ON tal.`MARKETPLACE_LISTING_ID`=ttf.`MARKETPLACE_LISTING_ID`\n"
//                    + " SET tal.`LAST_LISTED_PRICE`=ttf.`LAST_LISTED_PRICE`,tal.`LAST_LISTED_QUANTITY`=ttf.`LAST_LISTED_QUANTITY`,tal.`LAST_MODIFIED_BY`=?,tal.`LAST_MODIFIED_DATE`=?\n"
//                    + " WHERE tal.`MARKETPLACE_ID`=? AND tal.`MARKETPLACE_LISTING_ID`=ttf.`MARKETPLACE_LISTING_ID`";
            sql1 = "UPDATE pm_temp_available_listing ttp LEFT JOIN `pm_manufacturer` tp ON tp.`MANUFACTURER_NAME` = ttp.`MANUFACTURER_NAME`\n"
                    + " SET ttp.`MANUFACTURER_ID` = tp.MANUFACTURER_ID WHERE tp.`MANUFACTURER_NAME` = ttp.`MANUFACTURER_NAME`";

            jdbcTemplate.update(sql1);

            sql = "INSERT IGNORE INTO pm_available_listing (\n"
                    + "  `SKU`,\n"
                    + "  `MARKETPLACE_LISTING_ID`,\n"
                    + "  `LAST_LISTED_PRICE`,\n"
                    + "  `LAST_LISTED_QUANTITY`,\n"
                    + "  `PACK`,\n"
                    + "  `MARKETPLACE_ID`,\n"
                    + "  `LAST_MODIFIED_BY`,\n"
                    + "  `LAST_MODIFIED_DATE`\n"
                    + ") \n"
                    + "(SELECT \n"
                    + "  ttl.`SKU`,\n"
                    + "  ttl.`MARKETPLACE_LISTING_ID`,\n"
                    + "  ttl.`LAST_LISTED_PRICE`,\n"
                    + "  ttl.`LAST_LISTED_QUANTITY`,\n"
                    + "  ttl.`PACK`,\n"
                    + "  ?,\n"
                    + "  ?,\n"
                    + "  ?\n"
                    + "FROM \n"
                    + "  pm_temp_available_listing ttl) \n"
                    + "ON DUPLICATE KEY \n"
                    + "UPDATE \n"
                    + "`MARKETPLACE_LISTING_ID`= ttl.`MARKETPLACE_LISTING_ID`,\n"
                    + "`LAST_LISTED_PRICE` = ttl.`LAST_LISTED_PRICE`,\n"
                    + "`LAST_LISTED_QUANTITY` = ttl.`LAST_LISTED_QUANTITY`,\n"
                    + "`PACK` = ttl.`PACK`,\n"
                    + "`LAST_MODIFIED_BY`=?,\n"
                    + "`LAST_MODIFIED_DATE`= ? ";

            String sql2 = "INSERT IGNORE INTO pm_mpn_sku_mapping (\n"
                    + "  `MANUFACTURER_MPN`,\n"
                    + "  `MANUFACTURER_ID`,\n"
                    + "  `SKU`,\n"
                    + "  `PACK`\n"
                    + ") \n"
                    + "(SELECT \n"
                    + "  ttl.`MPN`,\n"
                    + "  ttl.`MANUFACTURER_ID`,\n"
                    + "  ttl.`SKU`,\n"
                    + "  ttl.`PACK` \n"
                    + "FROM\n"
                    + "  pm_temp_available_listing ttl) \n"
                    + "ON DUPLICATE KEY \n"
                    + "UPDATE \n"
                    + "  `MANUFACTURER_MPN` = ttl.`MPN`,\n"
                    + "  `MANUFACTURER_ID` = ttl.`MANUFACTURER_ID`,\n"
                    + "  `SKU` = ttl.`SKU`,\n"
                    + "  `PACK` = ttl.`PACK` ;";

//            Map<String, Object> params = new HashMap<String, Object>();
//            params.put("marketplaceId",marketplaceId );
//            params.put("curUser", curUser);
//            params.put("curDate", curDate);
//            jdbcTemplate.update(sql,params);
            jdbcTemplate.update(sql, marketplaceId, curUser, curDate, curUser, curDate);
            jdbcTemplate.update(sql2);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Listing> exportMarketplaceFeesForExcel(int marketplaceId) {

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT tal.`MARKETPLACE_LISTING_ID`,tal.`SKU`,tal.`CURRENT_PRICE`,tal.`CURRENT_COMMISSION` FROM pm_available_listing tal WHERE tal.`MARKETPLACE_ID`=:marketplaceId");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("marketplaceId", marketplaceId);
            NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
            return nm.query(sql.toString(), params, new ExportMarketPlaceFeesListingRowMapper());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Listing getListingForDelete(String sku) {
        try {
            String sql = " SELECT pm.`MARKETPLACE_NAME`,pal.`MARKETPLACE_LISTING_ID`,pal.`SKU`,pmsm.`MANUFACTURER_MPN`,pal.`CURRENT_PRICE`,pal.`CURRENT_QUANTITY` \n"
                    + " FROM pm_available_listing pal\n"
                    + " LEFT JOIN pm_marketplace pm ON pm.`MARKETPLACE_ID`=pal.`MARKETPLACE_ID`\n"
                    + " LEFT JOIN pm_mpn_sku_mapping pmsm ON pmsm.`SKU`=pal.`SKU`\n"
                    + " WHERE pal.`SKU`=?";
            Object params[] = new Object[]{sku};
            return this.jdbcTemplate.queryForObject(sql, params, new RowMapper<Listing>() {
                @Override
                public Listing mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Listing listing = new Listing();
                    Marketplace m = new Marketplace();
                    m.setMarketplaceName(rs.getString("MARKETPLACE_NAME"));
                    listing.setMarketplace(m);
                    listing.setMarketplaceListingId(rs.getString("MARKETPLACE_LISTING_ID"));
                    listing.setSku(rs.getString("SKU"));
                    listing.setCurrentPrice(rs.getDouble("CURRENT_PRICE"));
                    listing.setCurrentQunatity(rs.getInt("CURRENT_QUANTITY"));
                    listing.setProductMpn(rs.getString("MANUFACTURER_MPN"));
                    return listing;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
