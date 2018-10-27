/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspirant.performanceModsAdminTool.dao.impl;

import com.aspirant.performanceModsAdminTool.dao.ProductDao;
import com.aspirant.performanceModsAdminTool.framework.GlobalConstants;
import com.aspirant.performanceModsAdminTool.model.ChildOfChildCategory;
import com.aspirant.performanceModsAdminTool.model.ChildOfSubCategory;
import com.aspirant.performanceModsAdminTool.model.Company;
import com.aspirant.performanceModsAdminTool.model.CustomUserDetails;
import com.aspirant.performanceModsAdminTool.model.DTO.MarketplaceListingSkuDTO;
import com.aspirant.performanceModsAdminTool.model.DTO.mapper.MarketplaceListingSkuDTORowMapper;
import com.aspirant.performanceModsAdminTool.model.EmailerBlock;
import com.aspirant.performanceModsAdminTool.model.MainCategory;
import com.aspirant.performanceModsAdminTool.model.Manufacturer;
import com.aspirant.performanceModsAdminTool.model.MpnSkuMapping;
import com.aspirant.performanceModsAdminTool.model.Order;
import com.aspirant.performanceModsAdminTool.model.Product;
import com.aspirant.performanceModsAdminTool.model.ProductDetails;
import com.aspirant.performanceModsAdminTool.model.ProductStatus;
import com.aspirant.performanceModsAdminTool.model.SubCategory;
import com.aspirant.performanceModsAdminTool.model.TempWebsiteUpload;
import com.aspirant.performanceModsAdminTool.model.UploadFeed;
import com.aspirant.performanceModsAdminTool.model.rowmapper.ChildCategoryRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.CompanyRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.MainCategoryRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.ManufacturerRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.ProductDetailsRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.ProductDownloadListRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.ProductListRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.ProductRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.ProductStatusRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.SubCategoryRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.SubChildCategoryRowMapper;
import com.aspirant.performanceModsAdminTool.model.rowmapper.TempWebsiteUploadRowMapper;
import com.aspirant.performanceModsAdminTool.utils.LogUtils;
import com.aspirant.utils.DateUtils;
//import com.aspirantutils.StringUtils;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
public class ProductDaoImpl implements ProductDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Manufacturer> getListOfManufacturer() {
        String sql = "SELECT tm.* FROM tesy_manufacturer tm"
                + " where tm.ACTIVE ORDER BY tm.`MANUFACTURER_NAME`";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new ManufacturerRowMapper());
    }

    @Override
    public List<MainCategory> getListOfMainCategory() {
        String sql = "SELECT tmc.* ,  u1.`username` AS createdBy,u2.`username` AS lastModifiedBy FROM tesy_main_category tmc"
                + " LEFT JOIN `user` u1 ON u1.`USER_ID`=tmc.`CREATED_BY` "
                + " LEFT JOIN `user` u2 ON u2.`USER_ID`=tmc.`LAST_MODIFIED_BY`"
                + " where tmc.ACTIVE ORDER BY tmc.`MAIN_CATEGORY_DESC`";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new MainCategoryRowMapper());
    }

    @Override
    public List<SubCategory> getListOfSubCategory() {
        String sql = "SELECT tsc.* ,  u1.`username` AS createdBy,u2.`username` AS lastModifiedBy FROM tesy_sub_category tsc"
                + " LEFT JOIN `user` u1 ON u1.`USER_ID`=tsc.`CREATED_BY` "
                + " LEFT JOIN `user` u2 ON u2.`USER_ID`=tsc.`LAST_MODIFIED_BY`"
                + " LEFT JOIN tesy_category_mapping tcm ON tcm.`SUB_CATEGORY_ID`=tsc.`SUB_CATEGORY_ID`"
                + " WHERE tsc.ACTIVE GROUP BY tsc.`SUB_CATEGORY_ID`";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new SubCategoryRowMapper());
    }

    @Override
    public List<SubCategory> getSubCategoryListForMainCategory(int mainCategoryId) {
        String sql = "SELECT tsc.* ,  u1.`username` AS createdBy,u2.`username` AS lastModifiedBy FROM tesy_sub_category tsc"
                + " LEFT JOIN `user` u1 ON u1.`USER_ID`=tsc.`CREATED_BY` "
                + " LEFT JOIN `user` u2 ON u2.`USER_ID`=tsc.`LAST_MODIFIED_BY`"
                + " LEFT JOIN tesy_category_mapping tcm ON tcm.`SUB_CATEGORY_ID`=tsc.`SUB_CATEGORY_ID`"
                + " WHERE tcm.`MAIN_CATEGORY_ID`=? AND tsc.ACTIVE ORDER BY tsc.`SUB_CATEGORY_DESC`";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new SubCategoryRowMapper(), mainCategoryId);
    }

    @Override
    public List<Integer> getSubCategoryIdListByMainCategory(int mainCategoryId) {
        String sql = "SELECT tcm.`SUB_CATEGORY_ID` FROM tesy_category_mapping tcm WHERE tcm.`MAIN_CATEGORY_ID`=?";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.queryForList(sql, Integer.class, mainCategoryId);
    }

    /**
     * Method used to add new product
     *
     * @param product Object of Product model
     * @return productId
     */
    @Override
    @Transactional
    public int addProduct(Product product) {

        try {
            int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
            NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
            String sql;

            //Autogenerate performanceModsMPN when product created
            sql = "SELECT UPPER(CONCAT('TEL-', tm.`MANUFACTURER_CODE`,'-',:productMpn)) FROM tesy_manufacturer tm WHERE MANUFACTURER_ID=:manufacturerId";
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("productMpn", product.getManufacturerMpn());
            params.put("manufacturerId", product.getManufacturer().getManufacturerId());

            LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, params, GlobalConstants.TAG_SYSTEMLOG));
            String performanceModsMpn = nm.queryForObject(sql, params, String.class);
            params.clear();

            //Code for Image upload on Amazon S3 bucket
            AWSCredentials credentials = null;
            try {
                credentials = new ProfileCredentialsProvider().getCredentials();
            } catch (Exception e) {
                throw new AmazonClientException("Cannot load the credentials from the credential profiles file", e);
            }
            AmazonS3 s3 = new AmazonS3Client(credentials);
            Region usEast2 = Region.getRegion(Regions.US_EAST_2);
            s3.setRegion(usEast2);

            String bucketName = "performanceMods";
            BufferedInputStream bis = null;
            try {
                String key = product.getResizeImageFile().getOriginalFilename();
                bis = new BufferedInputStream(product.getResizeImageFile().getInputStream());
                ObjectMetadata omd = new ObjectMetadata();
                omd.setContentType(product.getResizeImageFile().getContentType());
                s3.putObject(new PutObjectRequest(bucketName, key, bis, omd).withCannedAcl(CannedAccessControlList.PublicRead));
                String resizeImageURL = "https://s3-" + usEast2 + ".amazonaws.com/performanceMods/" + product.getResizeImageFile().getOriginalFilename();
                product.setResizeImageUrl(resizeImageURL);
            } catch (IOException ex) {
            } catch (AmazonServiceException ase) {
            } catch (AmazonClientException ace) {
                System.out.println("Error Message: " + ace.getMessage());
            } finally {
                try {
                    bis.close();
                } catch (IOException ex) {
                }
            }

            SimpleJdbcInsert productInsert = new SimpleJdbcInsert(this.dataSource).withTableName("tesy_product").usingGeneratedKeyColumns("PRODUCT_ID");
            params.put("PRODUCT_NAME", product.getProductName());
            params.put("MANUFACTURER_ID", product.getManufacturer().getManufacturerId());
            params.put("MANUFACTURER_MPN", product.getManufacturerMpn());
            params.put("TITLE", product.getProductTitle());
            params.put("performanceMods_MPN", performanceModsMpn);
            params.put("MAP", product.getProductMap());
            params.put("MSRP", product.getProductMsrp());
            params.put("WEIGHT", product.getProductWeight());
            params.put("EST_SHIPPING_WT", product.getEstShippingWt());
            params.put("LENGTH", product.getProductLength());
            params.put("WIDTH", product.getProductWidth());
            params.put("HEIGHT", product.getProductHeight());
            params.put("UPC", product.getUpc());
            params.put("ASIN", product.getAsin());
            params.put("NEWEGG_ITEM_ID", product.getNeweggItemId());
            params.put("NEWEGG_BUSINESS_ITEM_ID", product.getNeweggB2BItemId());
            params.put("MAIN_CATEGORY_ID", product.getMainCategory().getMainCategoryId() == 0 ? null : product.getMainCategory().getMainCategoryId());
            params.put("SUB_CATEGORY1", product.getSubCategory1().getSubCategoryId() == 0 ? null : product.getSubCategory1().getSubCategoryId());
            params.put("SUB_CATEGORY2", product.getChildCategory().getChildCategoryId() == 0 ? null : product.getChildCategory().getChildCategoryId());
            params.put("SUB_CATEGORY3", product.getChildOfChildCategory().getSubChildCategoryId() == 0 ? null : product.getChildOfChildCategory().getSubChildCategoryId());
            params.put("SHORT_DESC", product.getShortDesc());
            params.put("LONG_DESC", product.getLongDesc());
            params.put("RETURNABLE", product.isReturnable());
            params.put("KEYWORDS", product.getKeywords());
            params.put("RESIZE_IMAGE_URL", product.getResizeImageUrl());
            params.put("NOTES", product.getNotes());
            params.put("CREATED_DATE", curDate);
            params.put("CREATED_BY", curUser);
            params.put("LAST_MODIFIED_DATE", curDate);
            params.put("LAST_MODIFIED_BY", curUser);
            params.put("ACTIVE", product.isActive());
            params.put("PRODUCT_STATUS_ID", product.getProductStatus().getProductStatusId());
            LogUtils.systemLogger.info(LogUtils.buildStringForLog("Inser into tesy_product :", params, GlobalConstants.TAG_SYSTEMLOG));
            int productId = productInsert.executeAndReturnKey(params).intValue();
            params.clear();
            //upload large images on CDN and insert into tesy_product_image one by one for product created
            if (productId != 0) {
                String largeImageURL = null;
                sql = "insert into tesy_product_image"
                        + " (PRODUCT_ID,LARGE_IMAGE_URL,ORDER_ID)"
                        + " VALUES(:productId,"
                        + " :largeImageUrl,"
                        + " :orderId)";

                if (!product.getLargeImageFile1().isEmpty()) {
                    try {
                        String key = product.getLargeImageFile1().getOriginalFilename();
                        bis = new BufferedInputStream(product.getLargeImageFile1().getInputStream());
                        ObjectMetadata omd = new ObjectMetadata();
                        omd.setContentType(product.getResizeImageFile().getContentType());
                        s3.putObject(new PutObjectRequest(bucketName, key, bis, omd).withCannedAcl(CannedAccessControlList.PublicRead));
                        //the accessibility of the file depends on permission/policy that set for the file.
                        largeImageURL = "https://s3-" + usEast2 + ".amazonaws.com/performanceMods/" + product.getLargeImageFile1().getOriginalFilename();
                    } catch (IOException ex) {
                    } catch (AmazonServiceException ase) {
                    } catch (AmazonClientException ace) {
                        System.out.println("Error Message: " + ace.getMessage());
                    } finally {
                        try {
                            bis.close();
                        } catch (IOException ex) {
                        }
                    }
                    params.put("productId", productId);
                    params.put("largeImageUrl", largeImageURL);
                    params.put("orderId", 1);

                    LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, params, GlobalConstants.TAG_SYSTEMLOG));
                    nm.update(sql, params);
                    params.clear();
                }
                if (!product.getLargeImageFile2().isEmpty()) {
                    try {
                        String key = product.getLargeImageFile2().getOriginalFilename();
                        bis = new BufferedInputStream(product.getLargeImageFile2().getInputStream());
                        ObjectMetadata omd = new ObjectMetadata();
                        omd.setContentType(product.getLargeImageFile2().getContentType());
                        s3.putObject(new PutObjectRequest(bucketName, key, bis, omd).withCannedAcl(CannedAccessControlList.PublicRead));
                        largeImageURL = "https://s3-" + usEast2 + ".amazonaws.com/performanceMods/" + product.getLargeImageFile2().getOriginalFilename();
                    } catch (IOException ex) {
                    } catch (AmazonServiceException ase) {
                    } catch (AmazonClientException ace) {
                        System.out.println("Error Message: " + ace.getMessage());
                    } finally {
                        try {
                            bis.close();
                        } catch (IOException ex) {
                        }
                    }
                    params.put("productId", productId);
                    params.put("largeImageUrl", largeImageURL);
                    params.put("orderId", 2);

                    LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, params, GlobalConstants.TAG_SYSTEMLOG));
                    nm.update(sql, params);
                    params.clear();
                }
                if (!product.getLargeImageFile3().isEmpty()) {
                    try {
                        String key = product.getLargeImageFile3().getOriginalFilename();
                        bis = new BufferedInputStream(product.getLargeImageFile3().getInputStream());
                        ObjectMetadata omd = new ObjectMetadata();
                        omd.setContentType(product.getLargeImageFile3().getContentType());
                        s3.putObject(new PutObjectRequest(bucketName, key, bis, omd).withCannedAcl(CannedAccessControlList.PublicRead));
                        largeImageURL = "https://s3-" + usEast2 + ".amazonaws.com/performanceMods/" + product.getLargeImageFile3().getOriginalFilename();
                    } catch (IOException ex) {
                    } catch (AmazonServiceException ase) {
                    } catch (AmazonClientException ace) {
                        System.out.println("Error Message: " + ace.getMessage());
                    } finally {
                        try {
                            bis.close();
                        } catch (IOException ex) {
                        }
                    }
                    params.put("productId", productId);
                    params.put("largeImageUrl", largeImageURL);
                    params.put("orderId", 3);

                    LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, params, GlobalConstants.TAG_SYSTEMLOG));
                    nm.update(sql, params);
                    params.clear();
                }
                if (!product.getLargeImageFile4().isEmpty()) {
                    try {
                        String key = product.getLargeImageFile4().getOriginalFilename();
                        bis = new BufferedInputStream(product.getLargeImageFile4().getInputStream());
                        ObjectMetadata omd = new ObjectMetadata();
                        omd.setContentType(product.getLargeImageFile4().getContentType());
                        s3.putObject(new PutObjectRequest(bucketName, key, bis, omd).withCannedAcl(CannedAccessControlList.PublicRead));
                        largeImageURL = "https://s3-" + usEast2 + ".amazonaws.com/performanceMods/" + product.getLargeImageFile4().getOriginalFilename();
                    } catch (IOException ex) {
                    } catch (AmazonServiceException ase) {
                    } catch (AmazonClientException ace) {
                        System.out.println("Error Message: " + ace.getMessage());
                    } finally {
                        try {
                            bis.close();
                        } catch (IOException ex) {
                        }
                    }
                    params.put("productId", productId);
                    params.put("largeImageUrl", largeImageURL);
                    params.put("orderId", 4);
                    LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, params, GlobalConstants.TAG_SYSTEMLOG));
                    nm.update(sql, params);
                }
            }
            return productId;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * Method used to get list of active products
     *
     * @return list of product status
     */
    @Override
    public List<ProductStatus> getListOfProductStatus() {
        String sql = "SELECT tps.* ,  u1.`username` AS createdBy,u2.`username` AS lastModifiedBy FROM tesy_product_status tps"
                + " LEFT JOIN `user` u1 ON u1.`USER_ID`=tps.`CREATED_BY` "
                + " LEFT JOIN `user` u2 ON u2.`USER_ID`=tps.`LAST_MODIFIED_BY`"
                + " WHERE tps.ACTIVE ORDER BY tps.`PRODUCT_STATUS_DESC`";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new ProductStatusRowMapper());
    }

    /**
     * Method used to get list of all products
     *
     * @param productStatusId product status id
     * @param manufacturerId Id of the manufacturer
     * @param performanceModsMpn performanceMods Mpn
     * @param productName Name of the product
     * @param pageNo Page number of pagination
     * @param warehouseMpn Mpn of warehouse
     * @param productMpn Actual Mpn of product
     * @return list of products
     */
    @Override
    public List<Product> getProductList(int productStatusId, int manufacturerId, String performanceModsMpn, String productName, int pageNo, String warehouseMpn, String productMpn, String startDate, String stopDate) {
        StringBuilder sql = new StringBuilder();
        try {
            sql.append("SELECT tp.`PRODUCT_ID`,tp.`PRODUCT_NAME`,tp.`performanceMods_MPN`,tp.`MANUFACTURER_ID`,tm.`MANUFACTURER_NAME`,tp.`MANUFACTURER_MPN`,tp.`MAP`,"
                    + " tp.`WEIGHT`, tp.`UPC`, tp.`PRODUCT_STATUS_ID`, tps.`PRODUCT_STATUS_DESC`,"
                    + " GROUP_CONCAT(tw.`WAREHOUSE_NAME`) WAREHOUSE_NAME,\n"
                    + " GROUP_CONCAT(twpm.`WAREHOUSE_MPN`) WAREHOUSE_MPN\n"
                    + " FROM tesy_product tp"
                    + " LEFT JOIN tesy_manufacturer tm ON tm.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`"
                    + " LEFT JOIN tesy_warehouse_product_mpn twpm ON twpm.`PRODUCT_ID`=tp.`PRODUCT_ID`"
                    + " LEFT JOIN tesy_product_status tps ON tps.`PRODUCT_STATUS_ID`=tp.`PRODUCT_STATUS_ID`"
                    + " LEFT JOIN tesy_warehouse tw ON tw.`WAREHOUSE_ID`=twpm.`WAREHOUSE_ID`"
                    + " WHERE tp.PRODUCT_ID");

            Map<String, Object> params = new HashMap<String, Object>();
            if (productStatusId != 0) {
                sql.append(" AND tp.PRODUCT_STATUS_ID=:productStatusId ");
                params.put("productStatusId", productStatusId);
            }

            if (manufacturerId != 0) {
                sql.append(" AND tp.MANUFACTURER_ID=:manufacturerId ");
                params.put("manufacturerId", manufacturerId);
            }

            if (productMpn != null && !productMpn.isEmpty()) {
                sql.append(" AND tp.MANUFACTURER_MPN=:productMpn ");
                params.put("productMpn", productMpn);
            }

            if (productName != null && !"".equals(productName)) {
                sql.append(" AND tp.PRODUCT_NAME=:productName ");
                params.put("productName", productName);
            }

            if (warehouseMpn != null && !warehouseMpn.isEmpty()) {
                sql.append(" AND twpm.`WAREHOUSE_MPN`=:warehouseMPN");
                params.put("warehouseMPN", warehouseMpn);
            }

            if (performanceModsMpn != null && !performanceModsMpn.isEmpty()) {
                sql.append(" AND tp.`performanceMods_MPN`=:performanceModsMpn");
                params.put("performanceModsMpn", performanceModsMpn);
            }

            if (startDate != null && stopDate != null) {
                startDate += " 00:00:00";
                stopDate += " 23:59:59";
                sql.append(" AND tp.`LAST_MODIFIED_DATE` BETWEEN :startDate AND :stopDate");
                params.put("startDate", startDate);
                params.put("stopDate", stopDate);
            }

            sql.append(" GROUP BY tp.`PRODUCT_ID` ORDER BY tp.`LAST_MODIFIED_DATE` DESC");

            if (pageNo != -1) {
                sql.append(" LIMIT ").append((pageNo - 1) * GlobalConstants.PAGE_SIZE).append(",").append(GlobalConstants.PAGE_SIZE);
            }

            NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

            LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), params, GlobalConstants.TAG_SYSTEMLOG));
            List<Product> list = nm.query(sql.toString(), params, new ProductListRowMapper());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Method used to get count of all products
     *
     * @param productStatusId product status id
     * @param manufacturerId Id of the manufacturer
     * @param performanceModsMpn performanceMods Mpn
     * @param productName Name of the product
     * @param warehouseMpn Mpn of warehouse
     * @param productMpn Actual Mpn of product
     * @return count of products
     */
    //Implemented pagination for list product
    @Override
    public int getProductListCount(int productStatusId, int manufacturerId, String performanceModsMpn, String productName, String warehouseMpn, String productMpn, String startDate, String stopDate) {

        StringBuilder sql = new StringBuilder("SELECT COUNT(t.productCount) FROM (SELECT COUNT(*) productCount FROM tesy_product tp \n"
                + "LEFT JOIN tesy_warehouse_product_mpn ON tesy_warehouse_product_mpn.`PRODUCT_ID`=tp.`PRODUCT_ID`\n"
                + "WHERE tp.`PRODUCT_ID`");

        Map<String, Object> params = new HashMap<String, Object>();
        if (productStatusId != 0) {
            sql.append(" AND tp.PRODUCT_STATUS_ID=:productStatusId ");
            params.put("productStatusId", productStatusId);
        }

        if (manufacturerId != 0) {
            sql.append(" AND tp.MANUFACTURER_ID=:manufacturerId ");
            params.put("manufacturerId", manufacturerId);
        }

        if (performanceModsMpn != null && performanceModsMpn != "") {
            sql.append(" AND tp.performanceMods_MPN=:performanceModsMpn");
            params.put("performanceModsMpn", performanceModsMpn);
        }

        if (productName != null && productName != "") {
            sql.append(" AND tp.PRODUCT_NAME=:productName ");
            params.put("productName", productName);
        }

        if (warehouseMpn != null && !warehouseMpn.isEmpty()) {
            sql.append(" AND tesy_warehouse_product_mpn.`WAREHOUSE_MPN`=:warehouseMpn");
            params.put("warehouseMpn", warehouseMpn);
        }

        if (productMpn != null && !productMpn.isEmpty()) {
            sql.append(" AND SUBSTR(tp.`performanceMods_MPN`,9)=:productMpn");
            params.put("productMpn", productMpn);
        }

        if (startDate != null && stopDate != null) {
            startDate += " 00:00:00";
            stopDate += " 23:59:59";
            sql.append(" AND tp.`LAST_MODIFIED_DATE` BETWEEN :startDate AND :stopDate");
            params.put("startDate", startDate);
            params.put("stopDate", stopDate);
        }

        sql.append(" GROUP BY tp.`PRODUCT_ID`) t");

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), params, GlobalConstants.TAG_SYSTEMLOG));
        Integer i = nm.queryForObject(sql.toString(), params, Integer.class);

        if (i == null) {
            return 0;
        } else {
            return i.intValue();
        }
    }

    /**
     * Method used to get product list of all products for export
     *
     * @param productStatusId product status id
     * @param manufacturerId Id of the manufacturer
     * @param performanceModsMpn performanceMods Mpn
     * @param productName Name of the product
     * @param warehouseMpn Mpn of warehouse
     * @param productMpn Actual Mpn of product
     * @return list of products
     */
    @Override
    public List<Product> getProductListForExcel(int productStatusId, int manufacturerId, String performanceModsMpn, String productName, String warehouseMpn, String productMpn) {
        StringBuilder sql = new StringBuilder();
        try {
            sql.append("SELECT tp.`PRODUCT_ID`,tp.`PRODUCT_NAME`,tp.`performanceMods_MPN`,tp.`MANUFACTURER_ID`,tm.`MANUFACTURER_NAME`,tp.`MANUFACTURER_MPN`,tp.`MAP`,\n"
                    + "tp.`WEIGHT`, tp.`UPC`, tp.`PRODUCT_STATUS_ID`, tps.`PRODUCT_STATUS_DESC`,\n"
                    + "GROUP_CONCAT(tw.`WAREHOUSE_NAME`) WAREHOUSE_NAME,\n"
                    + "GROUP_CONCAT(twpm.`WAREHOUSE_MPN`) WAREHOUSE_MPN\n"
                    + "FROM tesy_product tp\n"
                    + "LEFT JOIN tesy_manufacturer tm ON tm.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`\n"
                    + "LEFT JOIN tesy_warehouse_product_mpn twpm ON twpm.`PRODUCT_ID`=tp.`PRODUCT_ID`\n"
                    + "LEFT JOIN tesy_product_status tps ON tps.`PRODUCT_STATUS_ID`=tp.`PRODUCT_STATUS_ID`\n"
                    + "LEFT JOIN tesy_warehouse tw ON tw.`WAREHOUSE_ID`=twpm.`WAREHOUSE_ID`\n"
                    + "WHERE tp.PRODUCT_ID");

            Map<String, Object> params = new HashMap<String, Object>();
            if (productStatusId != 0) {
                sql.append(" AND tp.PRODUCT_STATUS_ID=:productStatusId ");
                params.put("productStatusId", productStatusId);
            }

            if (manufacturerId != 0) {
                sql.append(" AND tp.MANUFACTURER_ID=:manufacturerId ");
                params.put("manufacturerId", manufacturerId);
            }

            if (performanceModsMpn != null && performanceModsMpn != "") {
                sql.append(" AND tp.performanceMods_MPN=:performanceModsMpn ");
                params.put("performanceModsMpn", performanceModsMpn);
            }

            if (productName != null && productName != "") {
                sql.append(" AND tp.PRODUCT_NAME=:productName ");
                params.put("productName", productName);
            }

            if (warehouseMpn != null && !warehouseMpn.isEmpty()) {
                sql.append(" AND twpm.`WAREHOUSE_MPN`=:warehouseMPN");
                params.put("warehouseMPN", warehouseMpn);
            }

             if (productMpn != null && !productMpn.isEmpty()) {
                sql.append(" AND SUBSTR(tp.`performanceMods_MPN`,9)=:productMpn");
                params.put("productMpn", productMpn);
            }

            sql.append(" GROUP BY tp.`PRODUCT_ID` ORDER BY tp.`LAST_MODIFIED_DATE` DESC");

            NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
            LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), params, GlobalConstants.TAG_SYSTEMLOG));
            List<Product> list = nm.query(sql.toString(), params, new ProductListRowMapper());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product getProductById(int productId) {
        String sql = "SELECT tp.*, tmc.`MAIN_CATEGORY_DESC`,tsc1.`SUB_CATEGORY_DESC`, tscc.`CHILD_CATEGORY_DESC`,tcsc.`CHILD_SUB_CATEGORY_DESC`,\n"
                + "tm.`MANUFACTURER_NAME`, u1.`USERNAME` AS createdBy,u2.`USERNAME` AS lastModifiedBy,\n"
                + "tps.`PRODUCT_STATUS_DESC`, tpi1.`LARGE_IMAGE_URL` largeImageUrl1, tpi2.`LARGE_IMAGE_URL` largeImageUrl2,\n"
                + "tpi3.`LARGE_IMAGE_URL` largeImageUrl3, tpi4.`LARGE_IMAGE_URL` largeImageUrl4,\n"
                + "'' WAREHOUSE_NAME,'' WAREHOUSE_MPN\n"
                + "FROM tesy_product tp\n"
                + "LEFT JOIN tesy_main_category tmc ON tmc.`MAIN_CATEGORY_ID`=tp.`MAIN_CATEGORY_ID`\n"
                + "LEFT JOIN tesy_sub_category tsc1 ON tsc1.`SUB_CATEGORY_ID`=tp.`SUB_CATEGORY1`\n"
                + "LEFT JOIN tesy_sub_child_category tscc ON tscc.`CHILD_CATEGORY_ID`=tp.`SUB_CATEGORY2` \n"
                + "LEFT JOIN tesy_child_subset_category tcsc ON tcsc.`CHILD_SUB_CATEGORY_ID`=tp.`SUB_CATEGORY3` \n"
                + "LEFT JOIN tesy_manufacturer tm ON tm.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`\n"
                + "LEFT JOIN `user` u1 ON u1.`USER_ID`=tp.`CREATED_BY`\n"
                + "LEFT JOIN `user` u2 ON u2.`USER_ID`=tp.`LAST_MODIFIED_BY`\n"
                + "LEFT JOIN tesy_product_status tps ON tps.`PRODUCT_STATUS_ID`=tp.`PRODUCT_STATUS_ID`\n"
                + "LEFT JOIN tesy_product_image tpi1 ON tpi1.`PRODUCT_ID`=tp.`PRODUCT_ID` AND tpi1.`ORDER_ID`=1\n"
                + "LEFT JOIN tesy_product_image tpi2 ON tpi2.`PRODUCT_ID`=tp.`PRODUCT_ID` AND tpi2.`ORDER_ID`=2\n"
                + "LEFT JOIN tesy_product_image tpi3 ON tpi3.`PRODUCT_ID`=tp.`PRODUCT_ID` AND tpi3.`ORDER_ID`=3\n"
                + "LEFT JOIN tesy_product_image tpi4 ON tpi4.`PRODUCT_ID`=tp.`PRODUCT_ID` AND tpi4.`ORDER_ID`=4\n"
                + "WHERE tp.PRODUCT_ID=? GROUP BY tp.PRODUCT_ID";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.queryForObject(sql, new ProductRowMapper(), productId);
    }

    @Override
    @Transactional
    public void updateProduct(Product product) {
        try {
            String sqlString;
            int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
            String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);

            //Code for Image upload on Amazon S3 bucket
            AWSCredentials credentials = null;
            try {
                credentials = new ProfileCredentialsProvider().getCredentials();
            } catch (Exception e) {
                throw new AmazonClientException("Cannot load the credentials from the credential profiles file", e);
            }
            AmazonS3 s3 = new AmazonS3Client(credentials);
            Region usEast2 = Region.getRegion(Regions.US_EAST_2);
            s3.setRegion(usEast2);

            String bucketName = "performanceMods";
            BufferedInputStream bis = null;
            if (!product.getResizeImageFile().isEmpty()) {

                try {
                    String key = product.getResizeImageFile().getOriginalFilename();
                    bis = new BufferedInputStream(product.getResizeImageFile().getInputStream());
                    ObjectMetadata omd = new ObjectMetadata();
                    omd.setContentType(product.getResizeImageFile().getContentType());
                    s3.putObject(new PutObjectRequest(bucketName, key, bis, omd).withCannedAcl(CannedAccessControlList.PublicRead));
                    String resizeImageURL = "https://s3-" + usEast2 + ".amazonaws.com/performanceMods/" + product.getResizeImageFile().getOriginalFilename();
                    product.setResizeImageUrl(resizeImageURL);
                } catch (IOException ex) {
                } catch (AmazonServiceException ase) {
                } catch (AmazonClientException ace) {
                    System.out.println("Error Message: " + ace.getMessage());
                } finally {
                    try {
                        bis.close();
                    } catch (IOException ex) {
                    }
                }
            }

            //delete existing large images for respective product
            sqlString = "DELETE FROM tesy_product_image WHERE PRODUCT_ID=?";
            this.jdbcTemplate.update(sqlString, product.getProductId());

            NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
            sqlString = "UPDATE tesy_product tp"
                    + " SET tp.PRODUCT_NAME=:productName, tp.TITLE=:productTitle,"
                    + " tp.MAP=:productMap, tp.MSRP=:productMsrp, tp.WEIGHT=:productWeight,"
                    + " tp.`EST_SHIPPING_WT`=:estShippingWt, tp.`LENGTH`=:productLength, tp.`WIDTH`=:productWidth,"
                    + " tp.`HEIGHT`=:productHeight, tp.`UPC`=:upc,tp.`ASIN`=:asin,tp.`NEWEGG_ITEM_ID`=:neweggItemId,tp.`NEWEGG_BUSINESS_ITEM_ID`=:neweggB2BItemId, tp.`MAIN_CATEGORY_ID`=:mainCategoryId,"
                    + " tp.`SUB_CATEGORY1`=:subCategoryId1, tp.`SUB_CATEGORY2`=:subCategoryId2,tp.`SUB_CATEGORY3`=:subCategoryId3, "
                    + " tp.`SHORT_DESC`=:shortDesc, tp.`LONG_DESC`=:longDesc,"
                    + " tp.`RETURNABLE`=:returnable, tp.`KEYWORDS`=:keywords, tp.`RESIZE_IMAGE_URL`=:resizeImageUrl,"
                    + " tp.`NOTES`=:notes, tp.`LAST_MODIFIED_DATE`=:lastModifiedDate,"
                    + " tp.`LAST_MODIFIED_BY`=:lastModifiedBy, tp.`ACTIVE`=:active"
                    + " WHERE tp.`PRODUCT_ID`=:productId";

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("productName", product.getProductName());
            params.put("productTitle", product.getProductTitle());
            params.put("productMap", product.getProductMap());
            params.put("productMsrp", product.getProductMsrp());
            params.put("productWeight", product.getProductWeight());
            params.put("estShippingWt", product.getEstShippingWt());
            params.put("productLength", product.getProductLength());
            params.put("productWidth", product.getProductWidth());
            params.put("productHeight", product.getProductHeight());
            params.put("upc", product.getUpc());
            params.put("asin", product.getAsin());
            params.put("neweggItemId", product.getNeweggItemId().isEmpty() ? null : product.getNeweggItemId());
            params.put("neweggB2BItemId", product.getNeweggB2BItemId().isEmpty() ? null : product.getNeweggB2BItemId());
            params.put("mainCategoryId", product.getMainCategory().getMainCategoryId() == 0 ? null : product.getMainCategory().getMainCategoryId());
            params.put("subCategoryId1", product.getSubCategory1().getSubCategoryId() == 0 ? null : product.getSubCategory1().getSubCategoryId());
            params.put("subCategoryId2", product.getChildCategory().getChildCategoryId() == 0 ? null : product.getChildCategory().getChildCategoryId());
            params.put("subCategoryId3", product.getChildOfChildCategory().getSubChildCategoryId() == 0 ? null : product.getChildOfChildCategory().getSubChildCategoryId());
            params.put("shortDesc", product.getShortDesc());
            params.put("longDesc", product.getLongDesc());
            params.put("returnable", product.isReturnable());
            params.put("keywords", product.getKeywords());
            params.put("resizeImageUrl", product.getResizeImageUrl());
            params.put("notes", product.getNotes());
            params.put("lastModifiedDate", curDate);
            params.put("lastModifiedBy", curUser);
            params.put("active", product.isActive());
            params.put("productId", product.getProductId());

            LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
            nm.update(sqlString, params);
            params.clear();

            String largeImageURL = null;
            sqlString = "insert into tesy_product_image"
                    + " (PRODUCT_ID,LARGE_IMAGE_URL,ORDER_ID)"
                    + " VALUES(:productId,"
                    + " :largeImageUrl,"
                    + " :orderId)";

            //if large image1 file is selected then upload image on CDN and update URL
            if (!product.getLargeImageFile1().isEmpty()) {
                try {
                    String key = product.getLargeImageFile1().getOriginalFilename();
                    bis = new BufferedInputStream(product.getLargeImageFile1().getInputStream());
                    ObjectMetadata omd = new ObjectMetadata();
                    omd.setContentType(product.getResizeImageFile().getContentType());
                    s3.putObject(new PutObjectRequest(bucketName, key, bis, omd).withCannedAcl(CannedAccessControlList.PublicRead));
                    //the accessibility of the file depends on permission/policy that set for the file.
                    largeImageURL = "https://s3-" + usEast2 + ".amazonaws.com/performanceMods/" + product.getLargeImageFile1().getOriginalFilename();
                } catch (IOException ex) {
                } catch (AmazonServiceException ase) {
                } catch (AmazonClientException ace) {
                    System.out.println("Error Message: " + ace.getMessage());
                } finally {
                    try {
                        bis.close();
                    } catch (IOException ex) {
                    }
                }
                params.put("productId", product.getProductId());
                params.put("largeImageUrl", largeImageURL);
                params.put("orderId", 1);

                LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
                nm.update(sqlString, params);
                params.clear();
                params.clear();
                //if large image1 already exist then update URL of existing image
            } else if (!product.getLargeImageUrl1().isEmpty()) {
                params.put("productId", product.getProductId());
                params.put("largeImageUrl", product.getLargeImageUrl1());
                params.put("orderId", 1);

                LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
                nm.update(sqlString, params);
                params.clear();
            }

            //if large image2 file is selected then upload image on CDN and update URL
            if (!product.getLargeImageFile2().isEmpty()) {

                try {
                    String key = product.getLargeImageFile2().getOriginalFilename();
                    bis = new BufferedInputStream(product.getLargeImageFile2().getInputStream());
                    ObjectMetadata omd = new ObjectMetadata();
                    omd.setContentType(product.getLargeImageFile2().getContentType());
                    s3.putObject(new PutObjectRequest(bucketName, key, bis, omd).withCannedAcl(CannedAccessControlList.PublicRead));
                    largeImageURL = "https://s3-" + usEast2 + ".amazonaws.com/performanceMods/" + product.getLargeImageFile2().getOriginalFilename();
                } catch (IOException ex) {
                } catch (AmazonServiceException ase) {
                } catch (AmazonClientException ace) {
                    System.out.println("Error Message: " + ace.getMessage());
                } finally {
                    try {
                        bis.close();
                    } catch (IOException ex) {
                    }
                }
                params.put("productId", product.getProductId());
                params.put("largeImageUrl", largeImageURL);
                params.put("orderId", 2);

                LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
                nm.update(sqlString, params);
                params.clear();
                //if large image2 already exist then update URL of existing image
            } else if (!product.getLargeImageUrl2().isEmpty()) {
                params.put("productId", product.getProductId());
                params.put("largeImageUrl", product.getLargeImageUrl2());
                params.put("orderId", 2);

                LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
                nm.update(sqlString, params);
                params.clear();
            }

            //if large image3 file is selected then upload image on CDN and update URL
            if (!product.getLargeImageFile3().isEmpty()) {
                try {
                    String key = product.getLargeImageFile3().getOriginalFilename();
                    bis = new BufferedInputStream(product.getLargeImageFile3().getInputStream());
                    ObjectMetadata omd = new ObjectMetadata();
                    omd.setContentType(product.getLargeImageFile3().getContentType());
                    s3.putObject(new PutObjectRequest(bucketName, key, bis, omd).withCannedAcl(CannedAccessControlList.PublicRead));
                    String resizeImageURL = "https://s3-" + usEast2 + ".amazonaws.com/performanceMods/" + product.getLargeImageFile3().getOriginalFilename();
                    product.setResizeImageUrl(resizeImageURL);
                } catch (IOException ex) {
                } catch (AmazonServiceException ase) {
                } catch (AmazonClientException ace) {
                    System.out.println("Error Message: " + ace.getMessage());
                } finally {
                    try {
                        bis.close();
                    } catch (IOException ex) {
                    }
                }
                params.put("productId", product.getProductId());
                params.put("largeImageUrl", largeImageURL);
                params.put("orderId", 3);

                LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
                nm.update(sqlString, params);
                params.clear();
                //if large image3 already exist then update URL of existing image
            } else if (!product.getLargeImageUrl3().isEmpty()) {
                params.put("productId", product.getProductId());
                params.put("largeImageUrl", product.getLargeImageUrl3());
                params.put("orderId", 3);

                LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
                nm.update(sqlString, params);
                params.clear();
            }

            //if large image4 file is selected then upload image on CDN and update URL
            if (!product.getLargeImageFile4().isEmpty()) {
                try {
                    String key = product.getLargeImageFile4().getOriginalFilename();
                    bis = new BufferedInputStream(product.getLargeImageFile4().getInputStream());
                    ObjectMetadata omd = new ObjectMetadata();
                    omd.setContentType(product.getLargeImageFile4().getContentType());
                    s3.putObject(new PutObjectRequest(bucketName, key, bis, omd).withCannedAcl(CannedAccessControlList.PublicRead));
                    largeImageURL = "https://s3-" + usEast2 + ".amazonaws.com/performanceMods/" + product.getLargeImageFile4().getOriginalFilename();
                } catch (IOException ex) {
                } catch (AmazonServiceException ase) {
                } catch (AmazonClientException ace) {
                    System.out.println("Error Message: " + ace.getMessage());
                } finally {
                    try {
                        bis.close();
                    } catch (IOException ex) {
                    }
                }
                params.put("productId", product.getProductId());
                params.put("largeImageUrl", largeImageURL);
                params.put("orderId", 4);
                LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
                nm.update(sqlString, params);
                //if large image4 file is selected then upload image on CDN and update URL
            } else if (!product.getLargeImageUrl4().isEmpty()) {
                params.put("productId", product.getProductId());
                params.put("largeImageUrl", product.getLargeImageUrl4());
                params.put("orderId", 4);

                LogUtils.systemLogger.info(LogUtils.buildStringForLog(sqlString, params, GlobalConstants.TAG_SYSTEMLOG));
                nm.update(sqlString, params);
                params.clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int addSubCategory(String subCategoryDesc, boolean active) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);

        SimpleJdbcInsert subCategoryInsert = new SimpleJdbcInsert(this.dataSource).withTableName("tesy_sub_category").usingGeneratedKeyColumns("SUB_CATEGORY_ID");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("SUB_CATEGORY_DESC", subCategoryDesc);
        params.put("CREATED_DATE", curDate);
        params.put("CREATED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("ACTIVE", active);

        LogUtils.systemLogger.info(LogUtils.buildStringForLog("Insert into tesy_sub_category", params, GlobalConstants.TAG_SYSTEMLOG));
        int subCategoryId = subCategoryInsert.executeAndReturnKey(params).intValue();

        return subCategoryId;
    }

    //For autocomplete
    @Override
    public List<String> searchMpn(String term) {
        String sql = "SELECT performanceMods_MPN FROM tesy_product WHERE performanceMods_MPN LIKE '%" + term + "%'";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        return list;
    }

    @Override
    public List<String> searchProductName(String term) {
        String sql = "SELECT PRODUCT_NAME FROM tesy_product WHERE PRODUCT_NAME LIKE '%" + term + "%'";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        return list;
    }

    @Override
    public List<ProductDetails> getProductListWarehouseWise(String performanceModsMpn) {
        StringBuilder sql = new StringBuilder();
        try {

            sql.append("SELECT tw.`WAREHOUSE_ID`,tw.`WAREHOUSE_NAME`,tcwp.`WAREHOUSE_IDENTIFICATION_NO`,DATE(tcwp.`CREATED_DATE`) `CURRENT_DATE`,"
                    + " tcwp.`PRICE` CURRENT_PRICE,tcwp.`QUANTITY` CURRENT_QUANTITY, tpi.`LARGE_IMAGE_URL`"
                    + " FROM tesy_current_warehouse_product tcwp  "
                    + " LEFT JOIN tesy_warehouse tw ON tw.`WAREHOUSE_ID`=tcwp.`WAREHOUSE_ID`"
                    + " LEFT JOIN tesy_warehouse_product_mpn twpm ON twpm.`PRODUCT_ID`=tcwp.`PRODUCT_ID`"
                    + " LEFT JOIN tesy_product_image tpi ON tpi.`PRODUCT_ID`=tcwp.`PRODUCT_ID`"
                    + " AND twpm.`WAREHOUSE_ID`=tcwp.`WAREHOUSE_ID`"
                    + " LEFT JOIN tesy_product tp ON tp.`PRODUCT_ID`=tcwp.`PRODUCT_ID`\n"
                    + " WHERE tp.`performanceMods_MPN`=:performanceModsMpn "
                    + " GROUP BY tcwp.`WAREHOUSE_ID`");

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("performanceModsMpn", performanceModsMpn);

            NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

            LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), GlobalConstants.TAG_SYSTEMLOG));
            List<ProductDetails> ProductDetail = nm.query(sql.toString(), params, new ProductDetailsRowMapper());

            return ProductDetail;
        } catch (Exception e) {
            return null;
        }
    }

    //method call on view product page
    @Override
    public Product getProductInfoByperformanceModsMpn(String performanceModsMpn) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT tp.*, tmc.`MAIN_CATEGORY_DESC`,tsc1.`SUB_CATEGORY_DESC`, tscc.`CHILD_CATEGORY_DESC` ,tcsc.`CHILD_SUB_CATEGORY_DESC`,  "
                + "tm.`MANUFACTURER_NAME`, u1.`USERNAME` AS createdBy,u2.`USERNAME` AS lastModifiedBy,  "
                + "tps.`PRODUCT_STATUS_DESC`, tpi1.`LARGE_IMAGE_URL` largeImageUrl1, tpi2.`LARGE_IMAGE_URL` largeImageUrl2, "
                + "tpi3.`LARGE_IMAGE_URL` largeImageUrl3, tpi4.`LARGE_IMAGE_URL` largeImageUrl4,"
                + "'' WAREHOUSE_NAME,'' WAREHOUSE_MPN "
                + "FROM tesy_product tp  "
                + "LEFT JOIN tesy_main_category tmc ON tmc.`MAIN_CATEGORY_ID`=tp.`MAIN_CATEGORY_ID` "
                + "LEFT JOIN tesy_sub_category tsc1 ON tsc1.`SUB_CATEGORY_ID`=tp.`SUB_CATEGORY1` "
                + "LEFT JOIN tesy_sub_child_category tscc ON tscc.`CHILD_CATEGORY_ID`=tp.`SUB_CATEGORY2` "
                + "LEFT JOIN tesy_child_subset_category tcsc ON tcsc.`CHILD_SUB_CATEGORY_ID`=tp.`SUB_CATEGORY3` \n"
                + "LEFT JOIN tesy_manufacturer tm ON tm.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID` "
                + "LEFT JOIN `user` u1 ON u1.`USER_ID`=tp.`CREATED_BY` "
                + "LEFT JOIN `user` u2 ON u2.`USER_ID`=tp.`LAST_MODIFIED_BY` "
                + "LEFT JOIN tesy_product_status tps ON tps.`PRODUCT_STATUS_ID`=tp.`PRODUCT_STATUS_ID` "
                + "LEFT JOIN tesy_product_image tpi1 ON tpi1.`PRODUCT_ID`=tp.`PRODUCT_ID` AND tpi1.`ORDER_ID`=1 "
                + "LEFT JOIN tesy_product_image tpi2 ON tpi2.`PRODUCT_ID`=tp.`PRODUCT_ID` AND tpi2.`ORDER_ID`=2 "
                + "LEFT JOIN tesy_product_image tpi3 ON tpi3.`PRODUCT_ID`=tp.`PRODUCT_ID` AND tpi3.`ORDER_ID`=3 "
                + "LEFT JOIN tesy_product_image tpi4 ON tpi4.`PRODUCT_ID`=tp.`PRODUCT_ID` AND tpi4.`ORDER_ID`=4 "
                + "WHERE tp.`performanceMods_MPN`=:performanceModsMpn");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("performanceModsMpn", performanceModsMpn);

        sql.append(" GROUP BY tp.PRODUCT_ID");

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), GlobalConstants.TAG_SYSTEMLOG));
        try {
            return nm.queryForObject(sql.toString(), params, new ProductRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<String> searchWarehouseMpn(String term) {
        String sql = "SELECT WAREHOUSE_MPN FROM tesy_warehouse_product_mpn WHERE WAREHOUSE_MPN LIKE '%" + term + "%'";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        return list;
    }

    @Override
    public List<String> searchProductMpn(String term) {
        String sql = "SELECT p.`MANUFACTURER_MPN` FROM tesy_product p WHERE p.`MANUFACTURER_MPN` LIKE '%" + term + "%'";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        List<String> list = jdbcTemplate.queryForList(sql, String.class);
        return list;
    }

    @Override
    public String getperformanceModsMpnByManufacturerMpn(String manufacturerMpn, int manufacturerId) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT tp.`performanceMods_MPN` FROM tesy_product tp"
                + " WHERE tp.`MANUFACTURER_MPN`= :manufacturerMpn "
                + " AND tp.`MANUFACTURER_ID`=:manufacturerId");

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("manufacturerMpn", manufacturerMpn);
        params.put("manufacturerId", manufacturerId);

        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), GlobalConstants.TAG_SYSTEMLOG));
        try {
            return nm.queryForObject(sql.toString(), params, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<MarketplaceListingSkuDTO> getMarketplaceListingsAndSku(String performanceModsMpn) {
        String sql = "SELECT tal.`SKU`,tal.`MARKETPLACE_LISTING_ID`,tal.`CURRENT_LISTED_DATE`,tal.`CURRENT_PRICE`,"
                + " tal.`CURRENT_QUANTITY`,tm.`MARKETPLACE_NAME`,tw.`WAREHOUSE_NAME` FROM tesy_product tp "
                + " LEFT JOIN tesy_mpn_sku_mapping tmsm ON tmsm.`MANUFACTURER_MPN`=tp.`MANUFACTURER_MPN`"
                + " LEFT JOIN tesy_available_listing tal ON tal.`SKU`=tmsm.`SKU`"
                + " LEFT JOIN tesy_marketplace tm ON tm.`MARKETPLACE_ID`=tal.`MARKETPLACE_ID`"
                + " LEFT JOIN tesy_warehouse tw ON tw.`WAREHOUSE_ID`=tal.`WAREHOUSE_ID`"
                + " WHERE tp.`performanceMods_MPN`=? AND tal.`SKU` IS NOT NULL";
        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new MarketplaceListingSkuDTORowMapper(), performanceModsMpn);
    }

    @Override
    public List<Order> getLowCountProductForDashbored(int count) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT tm.`MANUFACTURER_NAME`,tmsm.`MANUFACTURER_MPN`,tso.`MARKETPLACE_SKU` \n"
                    + "FROM tesy_order tso\n"
                    + "LEFT JOIN tesy_available_listing tal ON tso.`WAREHOUSE_ID`=tal.`WAREHOUSE_ID` \n"
                    + "LEFT JOIN tesy_mpn_sku_mapping tmsm ON tmsm.`SKU`=tal.`SKU`\n"
                    + "LEFT JOIN tesy_manufacturer tm ON tm.`MANUFACTURER_ID`=tmsm.`MANUFACTURER_ID`\n"
                    + "WHERE tal.`CURRENT_QUANTITY`=2");
            if (count == 1) {
                sql.append(" LIMIT 10");
            }
            return this.jdbcTemplate.query(sql.toString(), new RowMapper<Order>() {
                @Override
                public Order mapRow(ResultSet rs, int i) throws SQLException {
                    Order order = new Order();
                    order.setMarketplaceSku(rs.getString("MARKETPLACE_SKU"));

                    Manufacturer manufacturer = new Manufacturer();
                    manufacturer.setManufacturerName(rs.getString("MANUFACTURER_NAME"));
                    order.setManufacturer(manufacturer);

                    MpnSkuMapping mapping = new MpnSkuMapping();
                    mapping.setManufacturerMPN(rs.getString("MANUFACTURER_MPN"));
                    order.setMpnSkuMapping(mapping);
                    return order;

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Product> getMissingProductDataList(int pageNo) {
        try {

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT tsp.`PRODUCT_ID`,tm.`MANUFACTURER_NAME`,tsp.`PRODUCT_NAME`,tsp.`MANUFACTURER_MPN`,tsp.`TITLE`,tsp.`UPC`,tmc.`MAIN_CATEGORY_DESC`,tsp.`WEIGHT`,tsp.`LENGTH`,  "
                    + "tsp.`WIDTH`,tsp.`HEIGHT`,tsp.`MAIN_CATEGORY_ID`,tsp.`SUB_CATEGORY1`,tsp.`SUB_CATEGORY2`,tsp.`SHORT_DESC`,tsp.`RESIZE_IMAGE_URL`   "
                    + "FROM tesy_product tsp   "
                    + "LEFT JOIN tesy_main_category tmc ON tsp.`MAIN_CATEGORY_ID`=tmc.`MAIN_CATEGORY_ID`  "
                    + "LEFT JOIN tesy_manufacturer tm ON tm.`MANUFACTURER_ID`= tsp.`MANUFACTURER_ID`  "
                    + "LEFT JOIN tesy_product_image ti ON ti.`PRODUCT_ID`= tsp.`PRODUCT_ID` "
                    + "WHERE TRIM(tsp.`PRODUCT_NAME`) IS NULL   "
                    + "OR TRIM(tsp.`MANUFACTURER_MPN`) IS NULL  "
                    + "OR TRIM(tsp.`TITLE`) IS NULL  "
                    + "OR TRIM(tsp.`WEIGHT`) IS NULL  "
                    + "OR TRIM(tsp.`UPC`) IS NULL   "
                    + "OR TRIM(tsp.`MAIN_CATEGORY_ID`)IS NULL  "
                    + "OR TRIM(tsp.`SUB_CATEGORY1`) IS NULL   "
                    + "OR TRIM(tsp.`SUB_CATEGORY2`) IS NULL   "
                    + "OR TRIM(tsp.`SHORT_DESC`) IS NULL  "
                    + "OR TRIM(tsp.`LONG_DESC`) IS NULL   "
                    + "OR TRIM(tsp.`RESIZE_IMAGE_URL`) IS NULL");
            if (pageNo != -1) {
                sql.append(" LIMIT ").append((pageNo - 1) * GlobalConstants.PAGE_SIZE).append(",").append(GlobalConstants.PAGE_SIZE);
            }

            return this.jdbcTemplate.query(sql.toString(), new RowMapper<Product>() {
                @Override
                public Product mapRow(ResultSet rs, int i) throws SQLException {
                    Product product = new Product();
                    product.setProductId(rs.getInt("PRODUCT_ID"));
                    Manufacturer m1 = new Manufacturer();
                    m1.setManufacturerName(rs.getString("MANUFACTURER_NAME"));
                    product.setManufacturer(m1);
                    product.setManufacturerMpn(rs.getString("MANUFACTURER_MPN"));
                    product.setProductTitle(rs.getString("TITLE"));
                    product.setProductWeight(rs.getDouble("WEIGHT"));
                    product.setProductLength(rs.getDouble("LENGTH"));
                    product.setProductWidth(rs.getDouble("WIDTH"));
                    product.setProductHeight(rs.getDouble("HEIGHT"));
                    MainCategory m = new MainCategory();
                    m.setMainCategoryId(rs.getInt("MAIN_CATEGORY_ID"));
                    m.setMainCategoryDesc(rs.getString("MAIN_CATEGORY_DESC"));
                    product.setMainCategory(m);
                    product.setUpc(rs.getString("UPC"));
                    SubCategory s1 = new SubCategory();
                    s1.setSubCategoryId(rs.getInt("SUB_CATEGORY1"));
                    product.setSubCategory1(s1);
                    ChildOfSubCategory s2 = new ChildOfSubCategory();
                    s2.setChildCategoryId(rs.getInt("SUB_CATEGORY2"));
                    product.setChildCategory(s2);
                    product.setShortDesc(rs.getString("SHORT_DESC"));
                    product.setResizeImageUrl(rs.getString("RESIZE_IMAGE_URL"));
                    return product;

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getMissingProductDataListCount() {

        String sql = "SELECT COUNT(*)\n"
                + "FROM tesy_product tsp \n"
                + "LEFT JOIN tesy_main_category tmc ON tsp.`MAIN_CATEGORY_ID`=tmc.`MAIN_CATEGORY_ID`\n"
                + "LEFT JOIN tesy_manufacturer tm ON tm.`MANUFACTURER_ID`= tsp.`MANUFACTURER_ID` \n"
                + "WHERE TRIM(tsp.`PRODUCT_NAME`) IS NULL \n"
                + "OR TRIM(tsp.`MANUFACTURER_MPN`) IS NULL\n"
                + "OR TRIM(tsp.`TITLE`) IS NULL\n"
                + "OR TRIM(tsp.`WEIGHT`) IS NULL\n"
                + "OR TRIM(tsp.`UPC`) IS NULL \n"
                + "OR TRIM(tsp.`MAIN_CATEGORY_ID`)IS NULL\n"
                + "OR TRIM(tsp.`SUB_CATEGORY1`) IS NULL \n"
                + "OR TRIM(tsp.`SUB_CATEGORY2`) IS NULL \n"
                + "OR TRIM(tsp.`SHORT_DESC`) IS NULL\n"
                + "OR TRIM(tsp.`LONG_DESC`) IS NULL \n"
                + "OR TRIM(tsp.`RESIZE_IMAGE_URL`) IS NULL";

        return this.jdbcTemplate.queryForInt(sql);
    }

    @Override
    public List<Product> getProductListForDelet(String performanceModsMpn) {
        try {
            String sql = "SELECT tp.`PRODUCT_ID`,tp.`PRODUCT_NAME`,tp.`performanceMods_MPN`,tp.`MANUFACTURER_ID`,tm.`MANUFACTURER_NAME`,tp.`MANUFACTURER_MPN`,tp.`MAP`,\n"
                    + "tp.`WEIGHT`, tp.`UPC`, tp.`PRODUCT_STATUS_ID`, tps.`PRODUCT_STATUS_DESC`,\n"
                    + "GROUP_CONCAT(tw.`WAREHOUSE_NAME`) WAREHOUSE_NAME,\n"
                    + "GROUP_CONCAT(twpm.`WAREHOUSE_MPN`) WAREHOUSE_MPN\n"
                    + "FROM tesy_product tp\n"
                    + "LEFT JOIN tesy_manufacturer tm ON tm.`MANUFACTURER_ID`=tp.`MANUFACTURER_ID`\n"
                    + "LEFT JOIN tesy_warehouse_product_mpn twpm ON twpm.`PRODUCT_ID`=tp.`PRODUCT_ID`\n"
                    + "LEFT JOIN tesy_product_status tps ON tps.`PRODUCT_STATUS_ID`=tp.`PRODUCT_STATUS_ID`\n"
                    + "LEFT JOIN tesy_warehouse tw ON tw.`WAREHOUSE_ID`=twpm.`WAREHOUSE_ID`\n"
                    + "WHERE tp.performanceMods_MPN=?";
            Object[] param = {performanceModsMpn};

            return this.jdbcTemplate.query(sql, new ProductListRowMapper(), param);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    @Transactional
    public int deleteProductByProductID(int productId) {

        String sql;
        sql = "DELETE FROM tesy_current_warehouse_product WHERE PRODUCT_ID = ? ";
        this.jdbcTemplate.update(sql, productId);
        sql = "DELETE FROM tesy_product_image WHERE PRODUCT_ID =? ";
        this.jdbcTemplate.update(sql, productId);
        sql = "DELETE FROM tesy_warehouse_feed_data  WHERE PRODUCT_ID=?";
        this.jdbcTemplate.update(sql, productId);
        sql = "DELETE FROM tesy_warehouse_product_mpn  WHERE PRODUCT_ID=?";
        this.jdbcTemplate.update(sql, productId);
        sql = "DELETE FROM tesy_product WHERE PRODUCT_ID=?";
        this.jdbcTemplate.update(sql, productId);
        return 1;
    }

    @Override
    public List<ChildOfSubCategory> getListOfChildCategory() {

        String sql = "SELECT tsc.* , u1.`username` AS createdBy,u2.`username` AS lastModifiedBy FROM tesy_sub_child_category tsc\n"
                + "LEFT JOIN `user` u1 ON u1.`USER_ID`=tsc.`CREATED_BY` \n"
                + "LEFT JOIN `user` u2 ON u2.`USER_ID`=tsc.`LAST_MODIFIED_BY`\n"
                + "LEFT JOIN tesy_sub_child_category_mapping tcm ON tcm.`SUB_CHILD_CATEGORY_ID`=tsc.`CHILD_CATEGORY_ID`\n"
                + "WHERE tsc.ACTIVE GROUP BY tsc.`CHILD_CATEGORY_ID`";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new ChildCategoryRowMapper());
    }

    @Override
    public List<ChildOfSubCategory> getChildCategoryListForSubCategory(int subCategoryId) {
        String sql = "SELECT tsc.* ,  u1.`username` AS createdBy,u2.`username` AS lastModifiedBy \n"
                + "FROM tesy_sub_child_category tsc\n"
                + "LEFT JOIN `user` u1 ON u1.`USER_ID`=tsc.`CREATED_BY` \n"
                + "LEFT JOIN `user` u2 ON u2.`USER_ID`=tsc.`LAST_MODIFIED_BY`\n"
                + "LEFT JOIN tesy_sub_child_category_mapping tcm ON tcm.`SUB_CHILD_CATEGORY_ID`=tsc.`CHILD_CATEGORY_ID`\n"
                + "WHERE tcm.`SUB_CATEGORY_ID`=? AND tsc.ACTIVE ORDER BY tsc.`CHILD_CATEGORY_DESC`";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new ChildCategoryRowMapper(), subCategoryId);

    }

    @Override
    public List<ChildOfChildCategory> getsubChildCategoryListForSubCategory(int childCategoryId) {
        String sql = "SELECT tsc.* ,  u1.`username` AS createdBy,u2.`username` AS lastModifiedBy \n"
                + "FROM tesy_child_subset_category tsc\n"
                + "LEFT JOIN `user` u1 ON u1.`USER_ID`=tsc.`CREATED_BY` \n"
                + "LEFT JOIN `user` u2 ON u2.`USER_ID`=tsc.`LAST_MODIFIED_BY`\n"
                + "LEFT JOIN tesy_child_childsubset_category_mapping tcm ON tcm.`CHILD_OF_CHILD_CATEGORY_ID`=tsc.`CHILD_SUB_CATEGORY_ID`\n"
                + "WHERE tcm.`CHILD_CATEGORY_ID`=? AND tsc.ACTIVE ORDER BY tsc.`CHILD_SUB_CATEGORY_DESC`";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new SubChildCategoryRowMapper(), childCategoryId);

    }

    @Override
    public int addChildCategory(String childCategoryDesc, boolean active) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);

        SimpleJdbcInsert subCategoryInsert = new SimpleJdbcInsert(this.dataSource).withTableName("tesy_sub_child_category").usingGeneratedKeyColumns("CHILD_CATEGORY_ID");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("CHILD_CATEGORY_DESC", childCategoryDesc);
        params.put("CREATED_DATE", curDate);
        params.put("CREATED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("ACTIVE", active);

        LogUtils.systemLogger.info(LogUtils.buildStringForLog("Insert into tesy_sub_child_category", params, GlobalConstants.TAG_SYSTEMLOG));
        int subCategoryId = subCategoryInsert.executeAndReturnKey(params).intValue();

        return subCategoryId;
    }

    @Override
    public List<Integer> getChildCategoryIdListBySubCategory(int subCategoryId) {
        String sql = "SELECT tcm.`SUB_CHILD_CATEGORY_ID` FROM tesy_sub_child_category_mapping tcm WHERE tcm.`SUB_CATEGORY_ID`=?";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.queryForList(sql, Integer.class, subCategoryId);
    }

    @Override
    public List<Product> getDownloadProductList(int productStatusId, int manufacturerId, String productName, int pageNo, int mainCategoryId, int subCategoryId, int childCategoryId) {
        StringBuilder sql = new StringBuilder();
        try {
            sql.append(" SELECT tp.`PRODUCT_NAME`,tm.`MANUFACTURER_ID`,tm.`MANUFACTURER_NAME`,tp.`MANUFACTURER_MPN`,tp.`WEIGHT`,  "
                    + "tc.`MAIN_CATEGORY_DESC`,tcc.`CHILD_CATEGORY_ID`,ts.`SUB_CATEGORY_ID`,tc.`MAIN_CATEGORY_ID`,ts.`SUB_CATEGORY_DESC`,tcc.`CHILD_CATEGORY_DESC`,tp.`SHORT_DESC`, "
                    + "tp.`LONG_DESC`,tp.`RESIZE_IMAGE_URL`,ti.`LARGE_IMAGE_URL`  "
                    + "FROM tesy_product tp  "
                    + "LEFT JOIN tesy_manufacturer tm ON tm.`MANUFACTURER_ID`= tp.`MANUFACTURER_ID`  "
                    + "LEFT JOIN tesy_main_category tc ON tc.`MAIN_CATEGORY_ID`=tp.`MAIN_CATEGORY_ID` "
                    + "LEFT JOIN tesy_sub_category ts ON ts.`SUB_CATEGORY_ID`= tp.`SUB_CATEGORY1` "
                    + "LEFT JOIN tesy_sub_child_category tcc ON tcc.`CHILD_CATEGORY_ID` = tp.`SUB_CATEGORY2`  "
                    + "LEFT JOIN tesy_product_image ti ON ti.`PRODUCT_ID`= tp.`PRODUCT_ID` "
                    + "WHERE tp.`PRODUCT_ID` ");

            Map<String, Object> params = new HashMap<String, Object>();
            if (productStatusId != 0) {
                sql.append(" AND tp.PRODUCT_STATUS_ID=:productStatusId ");
                params.put("productStatusId", productStatusId);
            }

            if (manufacturerId != 0) {
                sql.append(" AND tp.MANUFACTURER_ID=:manufacturerId ");
                params.put("manufacturerId", manufacturerId);
            }

            if (productName != null && !"".equals(productName)) {
                sql.append(" AND tp.PRODUCT_NAME=:productName ");
                params.put("productName", productName);
            }

            if (mainCategoryId != 0) {
                sql.append(" AND tp.`MAIN_CATEGORY_ID`=:mainCategoryId ");
                params.put("mainCategoryId", mainCategoryId);
            }
            if (subCategoryId != 0) {
                sql.append(" AND tp.`SUB_CATEGORY1`=:subCategoryId ");
                params.put("subCategoryId", subCategoryId);
            }
            if (childCategoryId != 0) {
                sql.append(" AND tp.`SUB_CATEGORY2`=:childCategoryId ");
                params.put("childCategoryId", childCategoryId);
            }

            sql.append(" GROUP BY tp.`PRODUCT_ID` ORDER BY tp.`LAST_MODIFIED_DATE` DESC");

            if (pageNo != -1) {
                sql.append(" LIMIT ").append((pageNo - 1) * GlobalConstants.PAGE_SIZE).append(",").append(GlobalConstants.PAGE_SIZE);
            }

            NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

            LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), params, GlobalConstants.TAG_SYSTEMLOG));
            List<Product> list = nm.query(sql.toString(), params, new ProductDownloadListRowMapper());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public int getCountDownloadProductList(int productStatusId, int manufacturerId, String productName, int pageNo, int mainCategoryId, int subCategoryId, int childCategoryId) {

        StringBuilder sql = new StringBuilder();
        try {
            sql.append("SELECT COUNT(tp.`PRODUCT_ID`)  "
                    + "FROM tesy_product tp  "
                    + "WHERE tp.`PRODUCT_ID` ");

            Map<String, Object> params = new HashMap<String, Object>();
            if (productStatusId != 0) {
                sql.append(" AND tp.PRODUCT_STATUS_ID=:productStatusId ");
                params.put("productStatusId", productStatusId);
            }

            if (manufacturerId != 0) {
                sql.append(" AND tp.MANUFACTURER_ID=:manufacturerId ");
                params.put("manufacturerId", manufacturerId);
            }

            if (productName != null && !"".equals(productName)) {
                sql.append(" AND tp.PRODUCT_NAME=:productName ");
                params.put("productName", productName);
            }

            if (mainCategoryId != 0) {
                sql.append(" AND tp.`MAIN_CATEGORY_ID`=:mainCategoryId ");
                params.put("mainCategoryId", mainCategoryId);
            }
            if (subCategoryId != 0) {
                sql.append(" AND tp.`SUB_CATEGORY1`=:subCategoryId ");
                params.put("subCategoryId", subCategoryId);
            }
            if (childCategoryId != 0) {
                sql.append(" AND tp.`SUB_CATEGORY2`=:childCategoryId ");
                params.put("childCategoryId", childCategoryId);
            }

            NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);

            LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql.toString(), params, GlobalConstants.TAG_SYSTEMLOG));
            Integer i = nm.queryForObject(sql.toString(), params, Integer.class);

            if (i == null) {
                return 0;
            } else {
                return i.intValue();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public List<SubCategory> getAllSubCategoryList() {
        try {

            String sql = "SELECT tm.`SUB_CATEGORY_ID`,tm.`SUB_CATEGORY_DESC` FROM tesy_sub_category tm";
            return this.jdbcTemplate.query(sql, new RowMapper<SubCategory>() {
                @Override
                public SubCategory mapRow(ResultSet rs, int i) throws SQLException {
                    SubCategory subCategory = new SubCategory();
                    subCategory.setSubCategoryId(rs.getInt("SUB_CATEGORY_ID"));
                    subCategory.setSubCategoryDesc(rs.getString("SUB_CATEGORY_DESC"));
                    return subCategory;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }

    @Override
    public List<ChildOfSubCategory> getAllChildCategoryList() {
        try {

            String sql = "SELECT ts.`CHILD_CATEGORY_ID`,ts.`CHILD_CATEGORY_DESC` FROM tesy_sub_child_category ts";
            return this.jdbcTemplate.query(sql, new RowMapper<ChildOfSubCategory>() {
                @Override
                public ChildOfSubCategory mapRow(ResultSet rs, int i) throws SQLException {
                    ChildOfSubCategory subCategory = new ChildOfSubCategory();
                    subCategory.setChildCategoryId(rs.getInt("CHILD_CATEGORY_ID"));
                    subCategory.setChildCategoryDesc(rs.getString("CHILD_CATEGORY_DESC"));
                    return subCategory;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public List<ChildOfChildCategory> getListOfSubChildCategory() {
        String sql = "SELECT tsc.* ,  u1.`username` AS createdBy,u2.`username` AS lastModifiedBy FROM tesy_child_subset_category tsc"
                + " LEFT JOIN `user` u1 ON u1.`USER_ID`=tsc.`CREATED_BY` "
                + " LEFT JOIN `user` u2 ON u2.`USER_ID`=tsc.`LAST_MODIFIED_BY`"
                + " LEFT JOIN tesy_child_childsubset_category_mapping tcm ON tcm.`CHILD_OF_CHILD_CATEGORY_ID`=tsc.`CHILD_SUB_CATEGORY_ID`"
                + " WHERE tsc.ACTIVE GROUP BY tsc.`CHILD_SUB_CATEGORY_ID`";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new SubChildCategoryRowMapper());
    }

    @Override
    public List<Integer> getSubChildCategoryIdListByChildCategory(int childCategoryId) {

        String sql = "SELECT tcm.`CHILD_OF_CHILD_CATEGORY_ID` FROM tesy_child_childsubset_category_mapping tcm WHERE tcm.`CHILD_CATEGORY_ID`=?";

        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.queryForList(sql, Integer.class, childCategoryId);
    }

    @Override
    public int addSubChildCategory(String subChildCategoryDesc, boolean active) {
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);

        SimpleJdbcInsert subChildCategoryInsert = new SimpleJdbcInsert(this.dataSource).withTableName("tesy_child_subset_category").usingGeneratedKeyColumns("CHILD_SUB_CATEGORY_ID");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("CHILD_SUB_CATEGORY_DESC", subChildCategoryDesc);
        params.put("CREATED_DATE", curDate);
        params.put("CREATED_BY", curUser);
        params.put("LAST_MODIFIED_DATE", curDate);
        params.put("LAST_MODIFIED_BY", curUser);
        params.put("ACTIVE", active);

        LogUtils.systemLogger.info(LogUtils.buildStringForLog("Insert into tesy_child_subset_category", params, GlobalConstants.TAG_SYSTEMLOG));
        int subChildCategoryId = subChildCategoryInsert.executeAndReturnKey(params).intValue();

        return subChildCategoryId;

    }

    @Override
    public void loadWebsiteProductDataLocally(String path, int companyId) {
        NamedParameterJdbcTemplate nm = new NamedParameterJdbcTemplate(jdbcTemplate);
        int curUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
        String curDate = DateUtils.getCurrentDateString(DateUtils.IST, DateUtils.YMDHMS);
        String sql = "TRUNCATE TABLE `tel_easy_admin_tool`.`temp_website_upload`";
        this.jdbcTemplate.update(sql);
        LogUtils.systemLogger.info(LogUtils.buildStringForLog("Truncate temp_website_upload done.", GlobalConstants.TAG_SYSTEMLOG));
        //query load data from bulk order tracking csv file into tesy_temp_bulk_tracking
        //sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `tel_easy_admin_tool`.`temp_website_upload` CHARACTER SET 'latin1' FIELDS ESCAPED BY '\"' TERMINATED BY ',' LINES TERMINATED BY '\n' IGNORE 1 LINES (`MANUFACTURER_MPN`,`PRODUCT_NAME`,`MANUFACTURER_NAME`,`MAIN_CATEGORY_DESC`,`SUB_CATEGORY_DESC`,`CHILD_CATEGORY_DESC`,`CHILD_SUB_CATEGORY_DESC`,`SHORT_DESC`,`LONG_DESC`,`IMAGE_URL_1`,`IMAGE_URL_2`,`IMAGE_URL_3`,`IMAGE_URL_4`,`MANUFACTURER_ID`,`MAIN_CATEGORY_ID`,`SUB_CATEGORY_ID`,`CHILD_CATEGORY_ID`,`CHILD_SUB_CATEGORY_ID`) ";
        sql = "LOAD DATA LOCAL INFILE '" + path + "' INTO TABLE `tel_easy_admin_tool`.`temp_website_upload` CHARACTER SET 'latin1' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 LINES  (`MANUFACTURER_MPN`,`PRODUCT_NAME`,`MANUFACTURER_NAME`,`MAIN_CATEGORY_DESC`,`SUB_CATEGORY_DESC`,`CHILD_CATEGORY_DESC`,`CHILD_SUB_CATEGORY_DESC`,`SHORT_DESC`,`LONG_DESC`,`IMAGE_URL_1`,`IMAGE_URL_2`,`IMAGE_URL_3`,`IMAGE_URL_4`,`MANUFACTURER_ID`,`MAIN_CATEGORY_ID`,`SUB_CATEGORY_ID`,`CHILD_CATEGORY_ID`,`CHILD_SUB_CATEGORY_ID`) ";
        this.jdbcTemplate.execute(sql);
        LogUtils.systemLogger.info(LogUtils.buildStringForLog("Load data done..", GlobalConstants.TAG_SYSTEMLOG));

        try {

            sql = "UPDATE temp_website_upload t SET t.`SUB_CATEGORY_DESC` = REPLACE(t.`SUB_CATEGORY_DESC`, 'â€™', '’')";
            jdbcTemplate.update(sql);

            sql = "UPDATE temp_website_upload t SET t.`SHORT_DESC` = REPLACE(t.`SHORT_DESC`, 'â€¢', '•'),\n"
                    + "t.`LONG_DESC` = REPLACE(t.`LONG_DESC`, 'â€¢', '•')";
            jdbcTemplate.update(sql);

            sql = "UPDATE temp_website_upload t SET t.`SHORT_DESC` = REPLACE(t.`SHORT_DESC`, 'â€“', '–'),\n"
                    + "t.`LONG_DESC` = REPLACE(t.`LONG_DESC`, 'â€“', '–')";
            jdbcTemplate.update(sql);

            sql = "UPDATE temp_website_upload tm\n"
                    + "LEFT JOIN tesy_manufacturer ttm ON ttm.`MANUFACTURER_NAME`=tm.`MANUFACTURER_NAME`\n"
                    + "SET tm.`MANUFACTURER_ID`=ttm.`MANUFACTURER_ID` WHERE ttm.`MANUFACTURER_NAME`=tm.`MANUFACTURER_NAME`;";
            jdbcTemplate.update(sql);

            sql = "UPDATE temp_website_upload tm \n"
                    + "LEFT JOIN tesy_product tp ON tp.`MANUFACTURER_ID`= tm.`MANUFACTURER_ID`\n"
                    + "SET tm.`performanceMods_MPN`= tp.`performanceMods_MPN`\n"
                    + "WHERE tm.`MANUFACTURER_ID`= tp.`MANUFACTURER_ID` \n"
                    + "AND tm.`MANUFACTURER_MPN`=tp.`MANUFACTURER_MPN`";
            jdbcTemplate.update(sql);

            sql = "UPDATE temp_website_upload tm \n"
                    + "LEFT JOIN tesy_product tp ON tp.`performanceMods_MPN`= tm.`performanceMods_MPN`\n"
                    + "SET tm.`PRODUCT_ID`=tp.`PRODUCT_ID` WHERE tp.`performanceMods_MPN`= tm.`performanceMods_MPN`;";
            jdbcTemplate.update(sql);

            sql = "UPDATE temp_website_upload tm \n"
                    + "LEFT JOIN tesy_main_category tc ON tc.`MAIN_CATEGORY_DESC`=tm.`MAIN_CATEGORY_DESC`\n"
                    + "SET tm.`MAIN_CATEGORY_ID`=tc.`MAIN_CATEGORY_ID` WHERE tc.`MAIN_CATEGORY_DESC`=tm.`MAIN_CATEGORY_DESC`;";
            jdbcTemplate.update(sql);

            sql = "UPDATE temp_website_upload tm \n"
                    + "LEFT JOIN tesy_sub_category ts ON ts.`SUB_CATEGORY_DESC`=tm.`SUB_CATEGORY_DESC`\n"
                    + "SET tm.`SUB_CATEGORY_ID` = ts.`SUB_CATEGORY_ID` WHERE ts.`SUB_CATEGORY_DESC`=tm.`SUB_CATEGORY_DESC`;";
            jdbcTemplate.update(sql);

            sql = "UPDATE temp_website_upload tm \n"
                    + "LEFT JOIN tesy_sub_child_category tsc ON tsc.`CHILD_CATEGORY_DESC`=tm.`CHILD_CATEGORY_DESC`\n"
                    + "SET tm.`CHILD_CATEGORY_ID` = tsc.`CHILD_CATEGORY_ID` WHERE tsc.`CHILD_CATEGORY_DESC`=tm.`CHILD_CATEGORY_DESC`;";
            jdbcTemplate.update(sql);

            sql = "UPDATE temp_website_upload tm \n"
                    + "LEFT JOIN tesy_child_subset_category tcsc ON tcsc.`CHILD_SUB_CATEGORY_DESC`=tm.`CHILD_SUB_CATEGORY_DESC`\n"
                    + "SET tm.`CHILD_SUB_CATEGORY_ID` = tcsc.`CHILD_SUB_CATEGORY_ID` WHERE tcsc.`CHILD_SUB_CATEGORY_DESC`=tm.`CHILD_SUB_CATEGORY_DESC`";
            jdbcTemplate.update(sql);

            sql = "UPDATE tesy_product tp\n"
                    + "LEFT JOIN temp_website_upload tw ON tw.`PRODUCT_ID`=tp.`PRODUCT_ID`\n"
                    + "SET\n"
                    + "tp.`PRODUCT_NAME`=tw.`PRODUCT_NAME`,\n"
                    + "tp.`MAIN_CATEGORY_ID`=tw.`MAIN_CATEGORY_ID`,\n"
                    + "tp.`SUB_CATEGORY1`=tw.`SUB_CATEGORY_ID`,\n"
                    + "tp.`SUB_CATEGORY2`=tw.`CHILD_CATEGORY_ID`,\n"
                    + "tp.`SUB_CATEGORY3`=tw.`CHILD_SUB_CATEGORY_ID`,\n"
                    + "tp.`SHORT_DESC`=tw.`SHORT_DESC`,\n"
                    + "tp.`LONG_DESC`=tw.`LONG_DESC`,\n"
                    + "tp.`WEBSITE_ID`=?\n"
                    + "WHERE tp.`performanceMods_MPN`=tw.`performanceMods_MPN` AND tp.`PRODUCT_ID`=tw.`PRODUCT_ID`;";
            jdbcTemplate.update(sql, companyId);

            sql = "SELECT twu.`PRODUCT_ID`,TRIM(twu.`IMAGE_URL_1`) AS i1,TRIM(twu.`IMAGE_URL_2`) AS i2,TRIM(twu.`IMAGE_URL_3`) AS i3,TRIM(twu.`IMAGE_URL_4`)AS i4\n"
                    + "FROM temp_website_upload twu\n"
                    + "LEFT JOIN tesy_product tp ON tp.`PRODUCT_ID` = twu.`PRODUCT_ID`\n"
                    + "WHERE tp.`PRODUCT_ID` = twu.`PRODUCT_ID` AND tp.`performanceMods_MPN` =twu.`performanceMods_MPN`";

            List<TempWebsiteUpload> list = this.jdbcTemplate.query(sql, new TempWebsiteUploadRowMapper());

            Map<String, Object> params = new HashMap<String, Object>();
            String sql1 = "insert into tesy_product_image"
                    + " (PRODUCT_ID,LARGE_IMAGE_URL,ORDER_ID)"
                    + " VALUES(:productId,"
                    + " :largeImageUrl,"
                    + " :orderId)";
            for (TempWebsiteUpload item : list) {

                if (!item.getImageURL1().isEmpty()) {
                    params.put("productId", item.getProductID());
                    params.put("largeImageUrl", item.getImageURL1());
                    params.put("orderId", 1);
                    nm.update(sql1, params);
                    params.clear();
                }

                if (!item.getImageURL2().isEmpty()) {
                    params.put("productId", item.getProductID());
                    params.put("largeImageUrl", item.getImageURL2());
                    params.put("orderId", 2);
                    nm.update(sql1, params);
                    params.clear();
                }

                if (!item.getImageURL3().isEmpty()) {
                    params.put("productId", item.getProductID());
                    params.put("largeImageUrl", item.getImageURL3());
                    params.put("orderId", 3);
                    nm.update(sql1, params);
                    params.clear();
                }

                if (!item.getImageURL4().isEmpty()) {
                    params.put("productId", item.getProductID());
                    params.put("largeImageUrl", item.getImageURL4());
                    params.put("orderId", 4);
                    nm.update(sql1, params);
                    params.clear();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String uploadImageToS3Bucket(UploadFeed image) {
        // add credentials file .aws folder
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException("Cannot load the credentials from the credential profiles file.", e);
        }
        AmazonS3 s3 = new AmazonS3Client(credentials);
        Region usEast2 = Region.getRegion(Regions.US_EAST_2);
        s3.setRegion(usEast2);
        // check here original bucket name and key
        String bucketName = "performanceMods";
        String key = image.getMultipartFile().getOriginalFilename();
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(image.getMultipartFile().getInputStream());
            ObjectMetadata omd = new ObjectMetadata();
            System.out.println("-------name of multipart file after name chnage  " + image.getMultipartFile().getOriginalFilename());
            omd.setContentType(image.getMultipartFile().getContentType());
            s3.putObject(new PutObjectRequest(bucketName, key, bis, omd).withCannedAcl(CannedAccessControlList.PublicRead));
            System.out.println("Uploaded");
            return "Uploaded";
        } catch (IOException ex) {
            System.out.println("error1");
            return "Error1";
        } catch (AmazonServiceException ase) {
            System.out.println("error2");
            return "Error2";
        } catch (AmazonClientException ace) {
            System.out.println("Error Message: " + ace.getMessage());
            return "Error3";
        } finally {

            try {
                bis.close();
            } catch (IOException ex) {
                // Logger.getLogger(UploadImageServiceImpl.class.getName()).log(Level.SEVERE,null, ex);
            }
        }
    }

    @Override
    public List<Company> getCompanyList(boolean active) {
        String sql = "SELECT tc.* FROM tkt_company tc";
        if (active) {
            sql += " WHERE tc.ACTIVE";
        }
        sql += " ORDER BY tc.`LAST_MODIFIED_DATE` DESC";
        LogUtils.systemLogger.info(LogUtils.buildStringForLog(sql, GlobalConstants.TAG_SYSTEMLOG));
        return this.jdbcTemplate.query(sql, new CompanyRowMapper());
    }

    
}
