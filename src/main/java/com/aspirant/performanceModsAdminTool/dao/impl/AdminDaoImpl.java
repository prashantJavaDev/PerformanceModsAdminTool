/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.AdminDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.Company;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.Customers;
import com.aspirant.performanceModsAdminTool.model.Listing;
import com.aspirant.performanceModsAdminTool.model.ShippingCriteria;
import com.aspirant.performanceModsAdminTool.model.ShippingDetails;
import com.aspirant.performanceModsAdminTool.model.rowmapper.CompanyRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.ShippingCriteriaRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.ShippingDetailsRowMapper;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.performanceModsAdminTool.utils.DateUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author shrutika
 */
@Repository
public class AdminDaoImpl implements AdminDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    @Qualifier("dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    @Transactional
    public void insertCategoryMapping(int mainCategoryId, int[] assignCategories) {
        String sql = "";
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource[] batchParams = new MapSqlParameterSource[assignCategories.length];
        int x = 0;

        //Delete all assigned categories from pm_sub_category.
        sql = "DELETE FROM pm_category_mapping WHERE MAIN_CATEGORY_ID =? ";
        this.jdbcTemplate.update(sql, mainCategoryId);

        //Assign default categories to main categories.
        sql = "INSERT INTO pm_category_mapping(CATEGORY_MAPPING_ID,MAIN_CATEGORY_ID,SUB_CATEGORY_ID)"
                + " VALUES (:categoryMappingId,:mainCategoryId,:subCategoryId)";
        for (int assign : assignCategories) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("categoryMappingId", null);
            params.put("mainCategoryId", mainCategoryId);
            params.put("subCategoryId", assign);
            batchParams[x] = new MapSqlParameterSource(params);
            x++;
        }

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        int[] resultList = nm.batchUpdate(sql, batchParams);
    }

    @Override
    @Transactional
    public void addNewListing(Listing listing) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        String sql = "INSERT INTO pm_available_listing(MARKETPLACE_ID,MARKETPLACE_LISTING_ID,SKU,CURRENT_COMMISSION,LAST_MODIFIED_BY,LAST_MODIFIED_DATE)"
                + " VALUES (:marketplaceId,:marketplaceListingId,:sku,:commission,:lastModifiedBy,:lastModifiedDate)";
        Map<String, Object> params = new HashMap<String, Object>();

        params.put("marketplaceId", listing.getMarketplace().getMarketplaceId());
        params.put("marketplaceListingId", listing.getMarketplaceListingId());
        params.put("sku", listing.getSku());
        params.put("commission", listing.getCommission());
        params.put("lastModifiedBy", curUser);
        params.put("lastModifiedDate", curDate);
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        nm.update(sql, params);

        String Query = "INSERT INTO pm_available_listing(MARKETPLACE_ID,MARKETPLACE_LISTING_ID,SKU,LAST_LISTED_PRICE,LAST_LISTED_QUANTITY,ACTIVE,PACK,LAST_MODIFIED_BY,LAST_MODIFIED_DATE)"
                + " VALUES (:marketplaceId,:marketplaceListingId,:sku,:lastListedPrice,:lastListedQuantity,:active,:pack,:lastModifiedBy,:lastModifiedDate)";
        Map<String, Object> params1 = new HashMap<String, Object>();

        params1.put("marketplaceId", listing.getMarketplace().getMarketplaceId());
        params1.put("marketplaceListingId", listing.getMarketplaceListingId());
        params1.put("sku", listing.getSku());
        params1.put("lastListedPrice", 0.0);
        params1.put("lastListedQuantity", 0);
        params1.put("active", 1);
        params1.put("pack", 1);
        params1.put("lastModifiedBy", curUser);
        params1.put("lastModifiedDate", curDate);
        NamedParameterJdbcTemplate nm1 = new NamedParameterJdbcTemplate(jdbcTemplate);
        nm1.update(Query, params1);

        String qry = "INSERT INTO pm_mpn_sku_mapping(MANUFACTURER_MPN,MANUFACTURER_ID,SKU,PACK)"
                + " VALUES (:productMpn,:manufacturerId,:sku,:pack)";
        Map<String, Object> params2 = new HashMap<String, Object>();
        params2.put("productMpn", listing.getProductMpn());
        params2.put("manufacturerId", listing.getManufacturer().getManufacturerId());
        params2.put("sku", listing.getSku());
        params2.put("pack", 1);
        NamedParameterJdbcTemplate nm2 = new NamedParameterJdbcTemplate(jdbcTemplate);
        nm2.update(qry, params2);
    }

    @Override
    public List<String> getSkuList(String productMpn, int manufacturerId) {
        String sql = "SELECT tm.`SKU` FROM pm_mpn_sku_mapping tm WHERE tm.`MANUFACTURER_MPN`=? AND tm.`MANUFACTURER_ID`=?";
        return this.jdbcTemplate.queryForList(sql, String.class, productMpn, manufacturerId);
    }

    @Override
    public String getperformanceModsMpnByMPN(String productMpn) {
        String sql = "SELECT tp.`performanceMods_MPN` FROM pm_product tp WHERE tp.`MANUFACTURER_MPN`=?";
        return this.jdbcTemplate.queryForObject(sql, String.class, productMpn);
    }

    @Override
    public int addCompany(Company company) {
        try {
            int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
            SimpleJdbcInsert companyInsert = new SimpleJdbcInsert(this.dataSource).withTableName("tkt_company").usingGeneratedKeyColumns("COMPANY_ID");
            java.util.Map<String, Object> params = new HashMap<String, Object>();
            params.put("COMPANY_NAME", company.getCompanyName());
            params.put("OWNER_NAME", company.getOwnerName());
            params.put("CONTACT_NUMBER", company.getContactNumber());
            params.put("COUNTRY_NAME", company.getCountryName());
            params.put("ACTIVE", 1);
            params.put("CREATED_BY", curUser);
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser);
            params.put("LAST_MODIFIED_DATE", curDate);
            int companyId = companyInsert.executeAndReturnKey(params).intValue();
            return companyId;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public List<Company> getCompanyList(boolean active) {
        String sql = "SELECT tc.*FROM tkt_company tc";
        if (active) {
            sql += " WHERE tc.ACTIVE";
        }
        sql += " ORDER BY tc.`LAST_MODIFIED_DATE` DESC";

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new CompanyRowMapper());
    }

    @Override
    public Company getcompanyBycompanyID(int companyId) {
        String sqlString = "SELECT tc.* FROM tkt_company tc"
                + " WHERE tc.`COMPANY_ID`=?";
        Object params[] = new Object[]{companyId};
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.queryForObject(sqlString, params, new CompanyRowMapper());
    }

    @Override
    public int updateCompany(Company company) {
        String sqlString;
        Object params[];
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        sqlString = "UPDATE tkt_company SET COMPANY_NAME=?, OWNER_NAME=?, CONTACT_NUMBER=?, COUNTRY_NAME=?, ACTIVE=?, LAST_MODIFIED_BY=?, LAST_MODIFIED_DATE=? WHERE COMPANY_ID=?";
        params = new Object[]{company.getCompanyName(), company.getOwnerName(), company.getContactNumber(), company.getCountryName(), company.isActive(), curUser, curDate, company.getCompanyId()};
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.update(sqlString, params);
    }

    @Override
    public List<ShippingCriteria> getshippingCriteriaList() {
        String sql = "SELECT * FROM pm_shipping_criteria";
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new ShippingCriteriaRowMapper());
    }

    @Override
    public int addWarehouseShipping(ShippingDetails shippingDetails, int criteriaId, int warehouseId) {
        try {

            int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            //System.out.println("Criteria Id :" + criteriaId);
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
            SimpleJdbcInsert warehouseInsert = new SimpleJdbcInsert(this.dataSource).withTableName("pm_shipping_details").usingGeneratedKeyColumns("SHIPPING_DETAILS_ID");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("SHIPPING_CRITERIA_ID", criteriaId);
            params.put("WAREHOUSE_ID", warehouseId);
            params.put("MIN_VALUE_PRICE", shippingDetails.getMinValuePrice());
            params.put("MIN_VALUE_PRICE_SHIPPING", shippingDetails.getMinValuePriceShipping());
            params.put("MAX_VALUE_PRICE", shippingDetails.getMaxValuePrice());
            params.put("MAX_VALUE_PRICE_SHIPPING", shippingDetails.getMaxValuePriceShipping());
            params.put("MIN_VALUE_WEIGHT", shippingDetails.getMinValueWeight());
            params.put("MIN_VALUE_WEIGHT_SHIPPING", shippingDetails.getMinValueWeightShipping());
            params.put("MAX_VALUE_WEIGHT", shippingDetails.getMaxValueWeight());
            params.put("MAX_VALUE_WEIGHT_SHIPPING", shippingDetails.getMaxValueWeightShipping());
            params.put("FLAT_RATE_VALUE", shippingDetails.getFlatRateValue());
            params.put("CREATED_BY", curUser);
            params.put("CREATED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser);
            params.put("LAST_MODIFIED_DATE", curDate);
            int shippingDetailsId = warehouseInsert.executeAndReturnKey(params).intValue();
            return shippingDetailsId;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public ShippingDetails getWarehouseShippingDetailsByDetailsId(int warehouseShippingDetailsId) {
        String sqlString = "SELECT tsd.* FROM pm_shipping_details tsd"
                + " WHERE tsd.`SHIPPING_DETAILS_ID`=?";
        Object params[] = new Object[]{warehouseShippingDetailsId};
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.queryForObject(sqlString, params, new ShippingDetailsRowMapper());
    }

    @Override
    public int addCustomer(Customers customer) {
        try {
            int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
            SimpleJdbcInsert companyInsert = new SimpleJdbcInsert(this.dataSource).withTableName("customers").usingGeneratedKeyColumns("CUSTOMER_ID");
            java.util.Map<String, Object> params = new HashMap<String, Object>();
//            params.put("CUSTOMER_NAME", company.getCompanyName());
//            params.put("ADDRESS", company.getOwnerName());
//            params.put("CITY", company.getContactNumber());
//            params.put("STATE", company.getCountryName());
//            params.put("ZIP_CODE", company.getCountryName());
//            params.put("COUNTRY", company.getCountryName());
//            params.put("PHONE_NO", company.getCountryName());
//            params.put("EMAIL", company.getCountryName());
            int customerId = companyInsert.executeAndReturnKey(params).intValue();
            return customerId;
        } catch (Exception e) {
            LogUtils.systemLogger.error(LogUtils.buildStringForLog(e, GlobalConstants.TAG_SYSTEMLOG));
            return 0;
        }
    }

    @Override
    public void insertChildCategoryMapping(int subCategoryId, int[] assignChildCategories) {
        String sql = "";
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource[] batchParams = new MapSqlParameterSource[assignChildCategories.length];
        int x = 0;

        //Delete all assigned categories from pm_sub_category.
        sql = "DELETE FROM pm_sub_child_category_mapping WHERE SUB_CATEGORY_ID =? ";
        this.jdbcTemplate.update(sql, subCategoryId);

        //Assign default categories to main categories.
        sql = "INSERT INTO pm_sub_child_category_mapping(SUB_CATEGORY_MAPPING_ID,SUB_CATEGORY_ID,SUB_CHILD_CATEGORY_ID)"
                + " VALUES (:categoryMappingId,:subCategoryId,:childCategoryId)";
        for (int assign : assignChildCategories) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("categoryMappingId", null);
            params.put("subCategoryId", subCategoryId);
            params.put("childCategoryId", assign);
            batchParams[x] = new MapSqlParameterSource(params);
            x++;
        }

       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        int[] resultList = nm.batchUpdate(sql, batchParams);
    }

    @Override
    public void insertSubChildCategoryMapping(int childCategoryId, int[] assignSubChildCategories) {
        String sql = "";
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        MapSqlParameterSource[] batchParams = new MapSqlParameterSource[assignSubChildCategories.length];
        int x = 0;

        sql = "DELETE FROM pm_child_childsubset_category_mapping WHERE CHILD_CATEGORY_ID =? ";
        this.jdbcTemplate.update(sql, childCategoryId);

        sql = "INSERT INTO  pm_child_childsubset_category_mapping(CHILD_CATEGORY_MAPPING_ID,CHILD_CATEGORY_ID,CHILD_OF_CHILD_CATEGORY_ID)\n"
                + " VALUES (:categoryMappingId,:childCategoryId,:childOfChildCategoryId)";

        for (int assign : assignSubChildCategories) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("categoryMappingId", null);
            params.put("childCategoryId", childCategoryId);
            params.put("childOfChildCategoryId", assign);
            batchParams[x] = new MapSqlParameterSource(params);
            x++;
        }
        
       // LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        int[] resultList = nm.batchUpdate(sql, batchParams);

    }
}
